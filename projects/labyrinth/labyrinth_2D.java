package projects.labyrinth;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class labyrinth_2D extends Labyrinth {

    private int size;
    private boolean[][] wall;

    //konstruktor
    public labyrinth_2D(int a) {
        this.size = a;
        this.wall = new boolean[a * 2 + 1][a * 2 + 1];

    }

    public boolean unvisitedCellsLeft(boolean[][] visited) {
        for (int i = 1; i < this.wall.length - 1; i += 2) {
            for (int k = 1; k < this.wall.length - 1; k += 2) {
                if (visited[i][k] == false) {
                    return true;
                }
            }
        }
        return false;
    }

    public Point getUnvisitedNeighbour(boolean[][] visited, Point cell) {
        ArrayList<Point> neighbour = new ArrayList<Point>();
        int[][] a = {{2, 0}, {0, 2}, {-2, 0}, {0, -2}};
        for (int[] dir : a) {

            try {
                if (visited[cell.x + dir[0]][cell.y + dir[1]] == false) {
                    neighbour.add(new Point(cell.x + dir[0], cell.y + dir[1]));
                }
            } catch (Exception x) {

            }


        }
        if (neighbour.size() == 0) {
            return null;
        } else {
            return neighbour.get((int) (Math.random() * neighbour.size()));
        }

    }

    public void generate() {
        for (int i = 0; i < this.wall.length; i++) {
            for (int k = 0; k < this.wall.length; k++) {
                this.wall[i][k] = true;
            }
        }
        for (int i = 1; i < this.wall.length - 1; i += 2) {
            for (int k = 1; k < this.wall.length - 1; k += 2) {
                this.wall[i][k] = false;
            }
        }


        int startY = (int) (Math.random() * size) * 2 + 1;
        this.wall[0][startY] = false;
        this.wall[this.wall.length - 1][(int) (Math.random() * size) * 2 + 1] = false;


        Point current = new Point(1,startY);
        boolean[][] visited = new boolean[size * 2 + 1][size * 2 + 1];
        Stack<Point> stack = new Stack<>();
        while (unvisitedCellsLeft(visited) == true) {
            Point nb = getUnvisitedNeighbour(visited, current);
            if (nb != null) {
                stack.push(current);
                this.wall[(nb.x + current.x) / 2][(nb.y + current.y) / 2] = false;
                current = nb;
                visited[current.x][current.y] = true;
            } else if(stack.empty() == false) {
                current = stack.pop();
            }
        }
    }


    public boolean collides(float x, float y) {

        if(x < 0 || y < 0 || x >= wall.length || y >= wall.length){
            return false;
        }

        if(wall[(int)x][(int)y] == true){
            return true;
        }else {
            return false;
        }
    }


    public ArrayList<Entity> getModel() {
        ArrayList<Entity> arrayList = new ArrayList<>();

        RawModel rawModel = OBJLoader.loadOBJ("models/cube", false);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/redpng"));
        TexturedModel model = new TexturedModel(rawModel, material);
        model.setTextureStretch(1);

        for (int i = 0; i < this.wall.length; i++) {
            for (int n = 0; n < this.wall.length; n++) {
                if (wall[i][n]) {
                    Entity e = new Entity(model);
                    e.setScale(0.5f, 2, 0.5f);
                    e.setPosition(i + 0.5f, 0, n + 0.5f);
                    arrayList.add(e);
                }
            }
        }

        return arrayList;
    }
}
