package ch.epfl.atran.models;

import ch.epfl.atran.data.RawModel;
import ch.epfl.atran.math.Turtle2D;
import ch.epfl.atran.math.Vector3f;
import org.lwjgl.opengl.GL30;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class LSystemFactory {

    public record TurtleConfig(int turningAngle, float forwardDistance) { }

    public static RawModel generate(String base, Vector3f initial, int depth, Function<Character, String> ruleSet, TurtleConfig config) {
        String result = evolve(base, ruleSet, depth);

        Turtle2D turtle = new Turtle2D(initial, 0);

        Deque<Pair<Vector3f, Integer>> stack = new ArrayDeque<>();
        List<Float> positions = new LinkedList<>();

        Vector3f currentPos = initial;

        for(char operation : result.toCharArray()) {
            push(positions, currentPos);
            switch (operation) {
                case 'F':
                    turtle.move(config.forwardDistance);
                    currentPos = turtle.getPosition();
                    break;
                case '+':
                    turtle.turn2D(config.turningAngle);
                    break;
                case '-':
                    turtle.turn2D(-config.turningAngle);
                case '[':
                    stack.addFirst(new Pair<>(turtle.getPosition(), turtle.getAngle()));
                    break;
                case ']':
                    Pair<Vector3f, Integer> top = stack.removeFirst();
                    turtle.reset(top.x, top.y);
            }
        }

        int[] indices = IntStream.range(0, positions.size() / 3).toArray();
        float[] posArray = new float[positions.size()];
        int i = 0;
        for(float f : positions) {
            posArray[i++] = f;
        }

        return new RawModel(posArray, indices, GL30.GL_POINTS);
    }

    private record Pair<X, Y>(X x, Y y){}

    private static void push(List<Float> list, Vector3f pos) {
        list.add(pos.x());
        list.add(pos.y());
        list.add(pos.z());
    }

    private static String evolve(String base, Function<Character, String> ruleSet) {
        StringBuilder builder = new StringBuilder();
        for(char c : base.toCharArray()) {
            builder.append(ruleSet.apply(c));
        }
        return builder.toString();
    }

    private static String evolve(String base, Function<Character, String> ruleSet, int n) {
        String str = base;
        for(int i = 0; i < n; i++) {
            str = evolve(str, ruleSet);
        }
        return str;
    }
}
