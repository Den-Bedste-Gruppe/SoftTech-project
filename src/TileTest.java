
public class TileTest {
	public static void main(String[] args) {
		Tile safetile = new SafeTile();
		safetile.incMarker();
		safetile.setShown();
		System.out.println(safetile.getMarker());
		System.out.println(safetile.isShown());
	}
}