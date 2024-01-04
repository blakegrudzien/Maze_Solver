/* Maze solving algorithm using each a stack implementation and a queue implementation
mazes are in the form of a 2d array of 1s and 0s
 */





 import org.w3c.dom.Node;
 import java.io.*;
 import java.util.*;
 
 /*
  * self-referential class to represent a position in a path
  */
 class Position{
     public int i;     //row
     public int j;     //column
     public char val;  //1, 0, or 'X'
 
     // reference to the previous position (parent) that leads to this position on a path
     Position parent;
 
     Position(int x, int y, char v){
         i=x; j = y; val=v;
     }
 
     Position(int x, int y, char v, Position p){
         i=x; j = y; val=v;
         parent=p;
     }
 
 }
 
 
 public class PathFinder {
 
     // main method: reads in maze file and finds path using both stackSearch and queueSearch
     public static void main(String[] args) throws IOException {
         if(args.length<1){
             System.err.println("***Usage: java PathFinder maze_file");
             System.exit(-1);
         }
 
         char [][] maze;
         maze = readMaze(args[0]);
         printMaze(maze);
         Position [] path = stackSearch(maze);
         if( path == null ){
             System.out.println("Maze is NOT solvable (no valid path identified in stackSearch).");
             printMaze(maze);
         } else {
             System.out.println("stackSearch Solution:");
             printPath(path);
             printMaze(maze);
         }
 
         char [][] maze2 = readMaze(args[0]);
         path = queueSearch(maze2);
         if( path == null ){
             System.out.println("Maze is NOT solvable (no valid path identified in queueSearch).");
             printMaze(maze);
         } else {
             System.out.println("queueSearch Solution:");
             printPath(path);
             printMaze(maze2);
         }
     }
 
 
 
 //Check for a path by using a Queue
    public static Position [] queueSearch(char [][] maze) {
 
 
 
        int N = maze.length;
 
        ArrayList<Position> visited = new ArrayList<Position>();
        visited = new ArrayList<Position>();
 
        Position temp = new Position(0,0,'.');
        Deque<Position> check = new ArrayDeque<Position>();
 
        check.add(temp);
 
        Stack<Position> line = new Stack<Position>();
 
 
        //Checks if more positions need to be checked, if not, forms the path
        while (!check.isEmpty()) {
 
            temp = check.remove();
 
            if (temp.i == N - 1 && temp.j == N - 1) {
                temp.val = 'X';
 
                int last = visited.size() -1;
                Position test = new Position(1,1,'0', temp);
                test = visited.get(last);
                Position par = new Position(1,1,'0', temp);
                par = test.parent;
 
                do{
                    maze[temp.i][temp.j] = 'X';
                    temp.val = 'X';
                    line.push(temp);
                    temp = temp.parent;
 
                }
                while (!(temp.i + temp.j == 0));
                maze[temp.i][temp.j] = 'X';
                temp.val = 'X';
                line.push(temp);
                temp = temp.parent;
 
 
 
                int len = line.size();
                Position pa[];
                int k = 0;
                pa = new Position[len];
 
 
                while (!line.isEmpty()) {
                    pa[k] = line.pop();
 
                    k = k+1;
                }
 
                return pa;
            }
 
 
 
            else {
                maze[temp.i][temp.j] = '.';
                visited.add(temp);
 
                //check for valid spots
                if (temp.i+1 < N && maze[temp.i + 1][temp.j] == '0') {
                    Position down_temp = new Position(temp.i + 1, temp.j, '.', temp);
                    check.add(down_temp);
                }
                if (temp.i-1 > -1 && maze[temp.i - 1][temp.j] == '0') {
                    Position up_temp = new Position(temp.i-1, temp.j, '.',temp);
 
 
                    check.add(up_temp);
                }
                if (temp.j+1 < N &&maze[temp.i][temp.j + 1] == '0') {
                    Position right_temp = new Position(temp.i, temp.j+1, '.',temp);
 
 
                    check.add(right_temp);
                }
                if (temp.j-1 > -1 &&maze[temp.i][temp.j - 1] == '0') {
                    Position left_temp = new Position(temp.i, temp.j-1, '.',temp);
 
                    check.add(left_temp);
                }
 
 
            }
 
        }
 
        return null;
    }
 
     //Check for a path by using a Queue
     public static Position [] stackSearch(char [][] maze) {
 
         int i = 0;
         int j = 0;
         int N = maze.length;
         char val = '.';
         ArrayList<Position> visited = new ArrayList<Position>();
 
         visited = new ArrayList<Position>();
 
 
         Position temp = new Position(i,j,val);
 
 
 
         Stack<Position> check = new Stack<Position>();
         check.push(temp);
 
         Stack<Position> line = new Stack<Position>();
 
 
         //Checks if more positions need to be checked, if not, forms the path
         while (!check.isEmpty()) {
 
             temp = check.pop();
 
             if (temp.i == N - 1 && temp.j == N - 1) {
                 temp.val = 'X';
 
 
                 int last = visited.size() -1;
                 Position test = new Position(1,1,'0', temp);
                 test = visited.get(last);
                 Position par = new Position(1,1,'0', temp);
                 par = test.parent;
 
 
                 do{
                    maze[temp.i][temp.j] = 'X';
                    temp.val = 'X';
                     line.push(temp);
                     temp = temp.parent;
 
                 }
                 while (!(temp.i + temp.j == 0));
                 maze[temp.i][temp.j] = 'X';
                 temp.val = 'X';
                 line.push(temp);
                 temp = temp.parent;
 
 
 
                 int len = line.size();
                 Position pa[];
 
                 int k = 0;
                 pa = new Position[len];
 
 
                 while (!line.isEmpty()) {
                     pa[k] = line.pop();
 
                     k = k+1;
                 }
 
                 return pa;
             }
 
 
 
             else {
                 maze[temp.i][temp.j] = '.';
                 visited.add(temp);
 
                 //check for valid spots
                 if (temp.i+1 < N && maze[temp.i + 1][temp.j] == '0') {
                         Position down_temp = new Position(temp.i + 1, temp.j, '.', temp);
                     check.push(down_temp);
                 }
                 if (temp.i-1 > -1 && maze[temp.i - 1][temp.j] == '0') {
                     Position up_temp = new Position(temp.i-1, temp.j, '.',temp);
 
 
                     check.push(up_temp);
                 }
                 if (temp.j+1 < N &&maze[temp.i][temp.j + 1] == '0') {
                     Position right_temp = new Position(temp.i, temp.j+1, '.',temp);
 
 
                     check.push(right_temp);
                 }
                 if (temp.j-1 > -1 &&maze[temp.i][temp.j - 1] == '0') {
                     Position left_temp = new Position(temp.i, temp.j-1, '.',temp);
 
                     check.push(left_temp);
                 }
             }
         }
         return null;
     }
 
         // prints path through maze
         public static void printPath (Position[]path){
             System.out.print("Path: ");
             for (Position p : path) {
                 System.out.print("(" + p.i + "," + p.j + ") ");
             }
             System.out.println();
         }
 
         // reads in maze from file
         public static char[][] readMaze (String filename) throws IOException {
             char[][] maze;
             Scanner scanner;
             try {
                 scanner = new Scanner(new FileInputStream(filename));
             } catch (IOException ex) {
                 System.err.println("*** Invalid filename: " + filename);
                 return null;
             }
 
             int N = scanner.nextInt();
             scanner.nextLine();
             maze = new char[N][N];
             int i = 0;
             while (i < N && scanner.hasNext()) {
                 String line = scanner.nextLine();
                 String[] tokens = line.split("\\s+");
                 int j = 0;
                 for (; j < tokens.length; j++) {
                     maze[i][j] = tokens[j].charAt(0);
                 }
                 if (j != N) {
                     System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
                     return null;
                 }
                 i++;
             }
             if (i != N) {
                 System.err.println("*** Invalid file: has wrong number of rows: " + i);
                 return null;
             }
             return maze;
         }
 
         // prints maze array
         public static void printMaze ( char[][] maze){
             System.out.println("Maze: ");
             if (maze == null || maze[0] == null) {
                 System.err.println("*** Invalid maze array");
                 return;
             }
 
             for (int i = 0; i < maze.length; i++) {
                 for (int j = 0; j < maze[0].length; j++) {
                     System.out.print(maze[i][j] + " ");
                 }
                 System.out.println();
             }
 
             System.out.println();
         }
 
 }
 