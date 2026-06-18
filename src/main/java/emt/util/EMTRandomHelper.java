package emt.util;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import ic2.api.item.ElectricItem;

public class EMTRandomHelper {

    public static ItemStack getChargedItem(Item item, int charge) {
        ItemStack stack = new ItemStack(item);
        ElectricItem.manager.charge(stack, charge, charge, true, false);
        return stack;
    }

    public static class Bolt {

        public ArrayList<Point> points = new ArrayList<Point>();
        public float angleZ = 0;
        public float angleY = 0;
        Random rnd = new Random();
        float dist = 0;
        float length = 0;
        float height = 0;

        public Bolt(float dist, float length, float height) {
            this.dist = dist;
            this.length = (length / 2f) + (rnd.nextInt((int) (length / 2f)));
            this.height = height;

            regenerate(this.dist, this.length, this.height);
        }

        public void regenerate() {
            regenerate(dist, length, height);
        }

        public void regenerate(float dist, float length, float height) {
            points.clear();

            angleZ = rnd.nextInt(360);
            angleY = rnd.nextInt(360);

            for (int i = 0; i < length / dist; i++) {
                float x = i * dist;
                float prevY = i == 0 ? 0 : points.get(i - 1).y;
                float prevZ = i == 0 ? 0 : points.get(i - 1).z;
                float y = prevY + (rnd.nextInt((int) height) - (height / 2));
                float z = prevZ + (rnd.nextInt((int) height) - (height / 2));

                byte color = (byte) ((198 / (length / dist)) * x);
                points.add(new Point(x, y, z, (byte) (255 - color), (byte) (255 - color), (byte) 255));
            }
        }
    }

    public static class Point {

        public float x = 0;
        public float y = 0;
        public float z = 0;
        public byte r = 0;
        public byte g = 0;
        public byte b = 0;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point(float x, float y, float z, byte r, byte g, byte b) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public Point(double x, double y, double z) {
            this.x = (float) x;
            this.y = (float) y;
            this.z = (float) z;
        }
    }
}
