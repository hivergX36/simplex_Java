public class Main {
    public static void main(String[] args) {
        Simplex simplex = new Simplex("data.txt");
        simplex.display_simplex();
        simplex.fill_dictionnary();
        simplex.display_dictionnary();
        simplex.resolve_simplexe();
        simplex.display_dictionnary();

    }
}