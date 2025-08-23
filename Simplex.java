import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Simplex {

    float[] x;
    float[] b;
    float[][] A;
    float[] Z;
    Dictionary dict_form;
    float zobj;
    int number_objectives;
    int number_constraints;
    int number_variables;

    Simplex(String name) {
        File input = new File(name);
        try (Scanner reader = new Scanner(input)) {
            String[] nums = reader.nextLine().split(" ");
            this.number_objectives = Integer.parseInt(nums[0]);
            this.number_variables = Integer.parseInt(nums[1]);
            this.number_constraints = Integer.parseInt(nums[2]);

            this.A = new float[number_constraints][number_variables];
            this.b = new float[number_constraints]; // Make sure this is initialized
            this.Z = new float[number_variables];
            this.dict_form = new Dictionary(number_variables, number_constraints);
            nums = reader.nextLine().split(" ");
            for (int i = 0; i < number_variables; i++) {
                this.Z[i] = Float.parseFloat(nums[i]);
            }

            for (int i = 0; i < number_constraints; i++) {
                nums = reader.nextLine().split(" ");
                if (nums.length != number_variables) {
                    System.out.println("Error: Number of variables does not match the expected count.");
                    return;
                }
                for (int j = 0; j < number_variables; j++) {
                    this.A[i][j] = Float.parseFloat(nums[j]);
                }
            }

            nums = reader.nextLine().split(" ");
            if (nums.length != number_constraints) {
                System.out.println("Error: Number of coefficients does not match the expected count.");
                return;
            } else {
                for (int i = 0; i < number_constraints; i++) {
                    this.b[i] = Float.parseFloat(nums[i]);
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in input file.");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("Unexpected end of file.");
            e.printStackTrace();
        }
    }

    void fill_dictionnary() {
        for (int i = 0; i < number_constraints; i++) {
            for (int j = 0; j < number_variables; j++) {
                this.dict_form.constraint_matrix[i][j] = A[i][j];

            }

        }
        for (int i = 0; i < number_constraints; i++) {
            for (int j = number_variables; j < number_variables + number_constraints; j++) {
                this.dict_form.constraint_matrix[i][j] = 0;

            }

            dict_form.constraint_matrix[i][number_variables + i] = 1;

        }
        for (int i = 0; i < number_variables; i++) {
            this.dict_form.Cj_Zj[i] = Z[i];
        }
        for (int i = 0; i < number_constraints; i++) {
            dict_form.Cj_Zj[number_variables + i] = 0;
            dict_form.constraint_matrix[i][number_constraints + number_variables] = b[i];

        }
        dict_form.Cj_Zj[number_variables + number_constraints] = 0;

    }

    void display_simplex() {
        System.out.println("Number of constraints: " + this.number_constraints + "\n");
        System.out.println("Number of Variables: " + this.number_variables + "\n");
        System.out.print("Objective function: ");
        for (int i = 0; i < number_variables; i++) {
            System.out.print(Z[i] + " ");
        }
        System.out.println();
        System.out.print("Constraint bounds: ");

        for (int i = 0; i < number_constraints; i++) {
            System.out.print(b[i] + " ");
        }
        System.out.println();
        System.out.print("Matrix constraints: ");

        for (int i = 0; i < number_constraints; i++) {
            for (int j = 0; j < number_variables; j++) {
                System.out.print(A[i][j] + " ");

            }
            System.out.println();
        }

    }

    void display_dictionnary() {
        System.out.println("Dictionnary:");
        System.out.println("Number of constraints: " + number_constraints + "\n");
        System.out.println("Number of Variables: " + number_variables + "\n");
        System.out.print("Cj_Zj: " + "\t");
        for (int i = 0; i < number_variables + number_constraints + 1; i++) {
            System.out.print(dict_form.Cj_Zj[i] + " ");
        }
        System.out.println("\n");
        System.out.println("Matrix constraints dictionnary: ");
        for (int i = 0; i < number_constraints; i++) {
            for (int j = 0; j < number_variables + number_constraints + 1; j++) {
                System.out.print(dict_form.constraint_matrix[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    void resolve_simplexe() {
        fill_dictionnary();
        int iteration_number = 0;
        boolean stop = false;
        while (stop == false) {
            for (int i = 0; i < number_variables; i++) {
                System.out.println("Ciji: " + dict_form.Cj_Zj[i]);
                if (dict_form.Cj_Zj[i] > 0) {
                    iteration_number += 1;
                    dict_form.choose_input_index();
                    dict_form.choose_pivot_line();
                    dict_form.pivot_dictionnary();
                    display_dictionnary();

                    if (iteration_number >= number_variables) {
                        stop = true;
                        System.out.println("Stop condition reached, exiting loop.");
                        break;
                    }

                }

            }

        }

    };
}