public class Dictionary {

    int input_index;
    int output_index;
    int number_constraints;
    int nbcol;
    int dictionary_number_variables;
    float[] Cj_Zj;
    float[][] constraint_matrix;

    Dictionary(int nb_var, int nb_bound) {
        this.number_constraints = nb_bound;
        this.dictionary_number_variables = nb_var + nb_bound;
        this.nbcol = dictionary_number_variables + 1;
        Cj_Zj = new float[dictionary_number_variables + 1];
        this.constraint_matrix = new float[number_constraints][dictionary_number_variables + 1];
    }

    void make_dictionary(float[] x, float[] b, float[][] A, float[] Z, int nbcontraints, int nbvar) {
        Cj_Zj = new float[dictionary_number_variables + 1];
        this.constraint_matrix = new float[number_constraints][dictionary_number_variables + 1];
        for (int i = 0; i < nbvar; i++) {
            Cj_Zj[i] = Z[i];
            for (int j = 0; j < nbcontraints; j++) {
                this.constraint_matrix[j][i] = A[j][i];
            }
        }
        for (int i = 0; i < nbcontraints; i++) {
            this.constraint_matrix[i][nbvar] = b[i];
        }
    }

    void choose_input_index() {
        double max = 0.0;
        this.input_index = 0;
        for (int i = 0; i < this.dictionary_number_variables; i++) {
            if (this.Cj_Zj[i] > max && this.Cj_Zj[i] > 0) {
                max = this.Cj_Zj[i];
                this.input_index = i;
            }
        }

    }

    void choose_pivot_line() {
        float test_min = 0;
        float min = this.constraint_matrix[0][this.dictionary_number_variables]
                / this.constraint_matrix[0][this.input_index];
        this.output_index = 0;
        for (int i = 1; i < number_constraints; i++) {
            test_min = this.constraint_matrix[i][this.dictionary_number_variables]
                    / this.constraint_matrix[i][this.input_index];
            if (test_min < min) {
                min = test_min;
                this.output_index = i;
            }
        }
    }

    void pivot_dictionnary() {
        float factor = 0;
        float div_factor = this.constraint_matrix[this.output_index][this.input_index];
        for (int i = 0; i < this.nbcol; i++) {
            this.constraint_matrix[this.output_index][i] = this.constraint_matrix[this.output_index][i] / div_factor;

        }
        ;
        for (int i = 0; i < number_constraints; i++) {
            if (i >= this.output_index && i <= this.output_index) {
                continue;
            } else {
                factor = this.constraint_matrix[i][this.input_index];
                for (int j = 0; j < nbcol; j++) {
                    this.constraint_matrix[i][j] = this.constraint_matrix[i][j]
                            - factor * this.constraint_matrix[this.output_index][j];
                }
            }
        }
        factor = Cj_Zj[this.input_index];
        for (int j = 0; j < nbcol; j++) {
            Cj_Zj[j] = Cj_Zj[j] - factor * this.constraint_matrix[this.output_index][j];
        }
    }

}
