import java.awt.*;


public class Segregation {


    public static void main(String[] args) {
        /*NUMBER OF (RED,BLUE) SQUARES WANTED OUT OF 2500 SQUARES TOTAL. LEFTOVERS ARE EMPTY*/
        int [][] coords = initAgents(1000, 500);


        /*GIVE SIMILARITY PERCENTAGE IN DECIMAL*/
        double similarity = .4;


        StdDraw.setXscale(0, 50);
        StdDraw.setYscale(0, 50);
        StdDraw.enableDoubleBuffering();
        drawGame(coords);
        drawGrid(50, 50);
        StdDraw.show();


        /* loop iterates until all agents are satisfied */
        while (boardSatisfaction(coords, similarity) < 1) {
            decideMove(coords, similarity);
            drawGrid(50, 50);
            StdDraw.show();
        }


    }


    /* determines total satisfaction percent among all agents on the board */
    public static double boardSatisfaction(int [][] coords, double similarity) {
        double satisfaction = 0;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (satisfaction(coords, i, j) > similarity) {
                    satisfaction++;
                }
            }
        }
        return (satisfaction/2500);
    }


    /* draws grid for graphics */
    public static void drawGrid(int width, int height) {
        StdDraw.setPenColor(Color.black);
        for (int x = 1; x < width; x++) {
            StdDraw.line(x, 0, x, height);
        }
        for (int y = 1; y < height; y++) {
            StdDraw.line(0, y, width, y);
        }
    }


    /* initializes agents to random spot on the grid */
    public static int[][] initAgents(int red, int blue){
        //init red (2)
        int coords[][] = new int[50][50];
        for (int i = 0; i < red; i++) {
            int row = StdRandom.uniformInt(50);
            int col = StdRandom.uniformInt(50);
            int temp = coords[row][col];

            if (temp == 1 || temp == 2) {
                i--;
            } else {
                coords[row][col] = 2;
            }
        }
        //int blue (1)
        for (int i = 0; i < blue; i++) {
            int row = StdRandom.uniformInt(50);
            int col = StdRandom.uniformInt(50);
            int temp = coords[row][col];
            if (temp == 1 || temp == 2) {
                i--;
            } else {
                coords[row][col] = 1;
            }
        }
        return coords;
    }




    /* draws agents at start */
    public static void drawGame(int[][] coords) {
        for (int row = 0; row < 50; row++) {
            for (int col = 0; col < 50; col++) {
                if (coords[row][col] == 1) {
                    StdDraw.setPenColor(Color.cyan);
                    StdDraw.filledSquare(row+.5, col+.5, 0.5);
                }
                if (coords[row][col] == 2) {
                    StdDraw.setPenColor(Color.pink);
                    StdDraw.filledSquare(row+.5, col+.5, 0.5);
                }
            }
        }
    }






    /* Decides if an agent should move to a new square by looking through array coords. Satisfaction returns a similarity percentage, this function checks for dissatisfaction. */
    public static void decideMove(int coords[][], double similarity) {
        double agentPercentage = 1;
        for (int j = 0; j < 50; j++){
            for (int i = 0; i < 50; i++) {
                if (coords[j][i] != 0) {
                    agentPercentage= satisfaction(coords, j, i);
                }
                if (agentPercentage < similarity) {
                    move(coords, j, i);
                }
            }
        }
    }




    /* Counts the number of each type of square around a given point in the coordinates array. Returns satisfaction as a percent.
     */
    public static double satisfaction(int[][] coords, int row, int col) {
        double countRed = 0;
        double countBlue = 0;
        double countEmpty = 0;
        double satisfaction = 1;
        for(int i =-1; i<=1; i++)
            for(int l = -1; l <= 1; l++){
                if ((row + i >= 0) && (col+l >= 0) && (row + i <= coords.length-1) && (col+l <= coords[0].length -1))
                    if(i+l != 0)
                        if (coords[row + i][col + l] == 0) {
                            countEmpty++;
                        } else if (coords[row + i][col + l] == 1) {
                            countBlue++;
                        } else if (coords[row + i][col + l] == 2) {
                            countRed++;
                        }
            }

        if (coords[row][col] == 1) {
            satisfaction = countBlue/(countBlue+countRed+countEmpty);
            return satisfaction;
        }
        if (coords[row][col] == 2) {
            satisfaction=countRed/(countBlue+countRed+countEmpty);
            return satisfaction;
        }
        return satisfaction;
    }




    /* Moves agent to new square, empties old square */
    public static void move (int coords[][], int j, int i) {
        StdDraw.setPenColor(Color.white);
        StdDraw.filledSquare(j+0.5, i+0.5, 0.5);

        while(coords[j][i] != 0) {
            int row = (int) (Math.random() * 50);
            int col = (int) (Math.random() * 50);
            if (coords[row][col] == 0) {
                if (coords[j][i] == 2) {
                    StdDraw.setPenColor(Color.pink);
                    StdDraw.filledSquare(row+.5, col+.5, 0.5);
                    coords[row][col] = 2;
                    coords[j][i]=0;
                }
                else if (coords[j][i] == 1) {
                    StdDraw.setPenColor(Color.cyan);
                    StdDraw.filledSquare(row+.5, col+.5, 0.5);
                    coords[row][col] = 1;
                    coords[j][i]=0;
                }
            }
        }
    }
}





