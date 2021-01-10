
public class BoardTest {
	public static void main(String[] args) {
		MineSweeperGame game = new MineSweeperGame(10, 10, 10);
		System.out.println(game);
		System.out.println("filling bombs");
		game.placeBombs(0, 0);
		System.out.println(game);
	}
}
