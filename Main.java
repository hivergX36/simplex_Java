public class Main {
    public static void main(String[] args) {
    Simplexe simplex = new Simplexe("data.txt");
    simplex.display_simplex();
    simplex.fill_dictionnary();
    simplex.display_dictionnary();
    simplex.resolve_simplexe();
    simplex.display_dictionnary();

    }
}