package doctor;

import java.util.*;

class Division {
    private int id;
    private List<Connection> connections;

    public Division(int id) {
        this.id = id;
        this.connections = new ArrayList<>();
    }

    public void addConnection(int target, double probability) {
        connections.add(new Connection(target, probability));
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public int getId() {
        return id;
    }
}

class Connection {
    private int targetDivision;
    private double probability;

    public Connection(int targetDivision, double probability) {
        this.targetDivision = targetDivision;
        this.probability = probability;
    }

    public int getTargetDivision() {
        return targetDivision;
    }

    public double getProbability() {
        return probability;
    }
}

class Lab {
    private Map<Integer, Division> divisions;
    private double[] probabilities;

    public Lab(int numDivisions) {
        this.divisions = new HashMap<>();
        this.probabilities = new double[numDivisions + 1];

        for (int i = 1; i <= numDivisions; i++) {
            divisions.put(i, new Division(i));
        }
    }

    public void addConnection(int from, int to, double probability) {
        divisions.get(from).addConnection(to, probability);
    }

    public void simulateDoctorTravel(int time) {
        dfs(1, time, 1.0);
    }

    private void dfs(int currentDivision, int time, double currentProbability) {
        if (time <= 0) {
            probabilities[currentDivision] += currentProbability;
            return;
        }

        Division division = divisions.get(currentDivision);
        for (Connection connection : division.getConnections()) {
            if (connection.getProbability() != 0.0) {
                int nextDivision = connection.getTargetDivision();
                double newProbability = currentProbability * connection.getProbability();
                dfs(nextDivision, time - 10, newProbability);
            }
        }
    }

    public Result findMostLikelyDivision() {
        double maxProbability = 0.0;
        int mostLikelyDivision = 1;

        for (int i = 1; i < probabilities.length; i++) {
            if (probabilities[i] > maxProbability) {
                maxProbability = probabilities[i];
                mostLikelyDivision = i;
            }
        }

        return new Result(mostLikelyDivision, maxProbability);
    }
}

class Result {
    private int division;
    private double probability;

    public Result(int division, double probability) {
        this.division = division;
        this.probability = probability;
    }

    public int getDivision() {
        return division;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return String.format("%d %.6f", division, probability);
    }
}

public class SolutionOOP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();

        while (testCases-- > 0) {
            int numDivisions = sc.nextInt();
            int numEdges = sc.nextInt();
            int time = sc.nextInt();

            Lab lab = new Lab(numDivisions);

            for (int i = 0; i < numEdges; i++) {
                int from = sc.nextInt();
                int to = sc.nextInt();
                double probability = sc.nextDouble();
                lab.addConnection(from, to, probability);
            }

            lab.simulateDoctorTravel(time);
            Result result = lab.findMostLikelyDivision();
            System.out.println(result);
        }
    }
}
