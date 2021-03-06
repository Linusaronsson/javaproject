package matchthree.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import matchthree.controller.UIController.View;
import matchthree.message.Message;
import matchthree.model.Jewel;
import matchthree.model.OpponentModel;
import matchthree.model.PlayerModel;
import matchthree.model.Settings;
import matchthree.view.BackButton;
import matchthree.view.Button;
import matchthree.view.MultiplayerView;

/**
 * View controller for `MultiplayerView`.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 * @author Erik Tran
 */
public class MultiplayerViewController
	implements ViewController
{
	/** Exit code - Network error. */
	private static final int EXIT_NETWORK = 3;
	
	/** Default game size. */
	private static final int GAME_SIZE = Settings.getGameSize();
	
	/** Default port number. */
	private static final int PORT_NUMBER = Settings.getPortNumber();
	
	/** ... */
	private InetAddress host = null;
	
	/** View to control. */
	private MultiplayerView multiplayerView = null;
	
	/** ... */
	private OpponentUIController opponentController = null;
	
	/** ... */
	private OpponentModel opponentModel = null;
	
	/** ... */
	private MatchThreeUIController playerController = null;
	
	/** ... */
	private PlayerModel playerModel = null;
	
	/** ... */
	private int port = 0;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 */
	private final class HoverListener
		implements MouseListener
	{
		/** ... */
		private Button target = null;
		
		/**
		 * Constructor.
		 *
		 * @author Erik Tran
		 * @param target ...
		 */
		private HoverListener(final Button target) {
			this.target = target;
		}
		
		@Override public void mouseClicked(final MouseEvent e) { }
		
		@Override public void mousePressed(final MouseEvent e) { }
		
		@Override public void mouseReleased(final MouseEvent e) { }
		
		@Override public void mouseEntered(final MouseEvent e) {
			target.setMask(Color.BLACK, 0.3f);
		}
		
		@Override public void mouseExited(final MouseEvent e) {
			target.setMask(Color.BLACK, 0.0f);
		}
	}
	
	/**
	 * Create `MultiplayerViewController`.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param parent       Parent view to use.
	 * @param uiController UI controller to use.
	 * @param settings     Application settings.
	 * @param board        ...
	 * @param host         ...
	 * @param port         ...
	 */
	public MultiplayerViewController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings,
		final Jewel[]      board,
		final InetAddress  host,
		final int          port)
	{
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		if (uiController == null) {
			throw new IllegalArgumentException(
				"`uiController` must not be null"
			);
		}
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}
		// NOTE: `board` may be null.
		if (host == null) {
			throw new IllegalArgumentException("`host` must not be null");
		}
		if (port < 0) {
			throw new IllegalArgumentException("`port` must be positive");
		}
		
		// Create view //
		multiplayerView = new MultiplayerView();
		
		// Create player model //
		playerModel = new PlayerModel(board, GAME_SIZE, host, port);
		playerModel.setGameStarted(true);
		
		// Create opponent model //
		Jewel[] opponentBoard = (board == null)
			? playerModel.getBoard()
			: board;
		opponentModel = new OpponentModel(opponentBoard, GAME_SIZE);
		
		// Send board to opponent if host //
		if (board == null) {
			playerModel.sendBoard(PORT_NUMBER);
		}
		
		// Create player view //
		playerController = new MatchThreeUIController(
			multiplayerView.getPlayer1(),
			uiController,
			settings,
			playerModel
		);
		
		// Create opponent view //
		opponentController = new OpponentUIController(
			multiplayerView.getPlayer2(),
			uiController,
			settings,
			opponentModel,
			port
		);
		
		// Add event listeners //
		multiplayerView.addBackListener(event -> {
			// Initiate connection //
			back();
		});
		BackButton back = multiplayerView.getBackButton();
		multiplayerView.addBackListener(new HoverListener(back));
		
		// Add view to parent //
		parent.add(multiplayerView);
		
		// Assign fields //
		this.host         = host;
		this.port         = port;
		this.uiController = uiController;
	}
	
	/**
	 * Close an ongoing multiplayer game (will close active sockets etc.).
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 */
	public void closeGame() {
		opponentController.close();
		Server.setInGame(false);
		try {
			Message message = new Message(Message.MessageType.END_GAME);
			DatagramSocket socket = new DatagramSocket();
			Server.sendDatagram(message, socket, host, port);
		} catch (final SocketException exception) {
			exception.printStackTrace();
		}
	}
	
	@Override
	public void closeView() {
		// Close game session //
		closeGame();
	}
	
	/**
	 * Handle back event.
	 *
	 * @author Erik Tran
	 */
	private void back() {
		uiController.changeView(View.MAIN_MENU);
	}
}
