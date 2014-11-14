package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OptionAnalysis {
    private Space space;
    private Set<Filler> fillers;

    public OptionAnalysis(double area) {
        space = new Space(area);
        fillers = new TreeSet<Filler>();
        fillers.add(new Filler(1.0, 1.0));
        fillers.add(new Filler(1.8, 2.0));
        fillers.add(new Filler(3.2, 4.0));
    }

    public Space evaluateSpace() {
        return fillArea(space.copy());
    }

    private Space fillArea(Space space) {
        List<Space> newSpaces = new ArrayList<Space>();
        for (Filler filler : fillers) {
            Space copy = space.copy();
            copy.addFiller(filler);
            copy = copy.isFull() ? copy : fillArea(copy);
            newSpaces.add(copy);
        }

        //process the filled spaces
        Space result = newSpaces.get(0);
        for (Space sp : newSpaces) {
            if (result.getCost() > sp.getCost()) {
                result = sp;
            }
        }
        return result;
    }

    protected class Space {
        private double cost;
        private double area;
        private double filledArea;
        private List<Filler> fillers;

        public Space(double area) {
            this.area = area;
            filledArea = 0.0;
            cost = 0.0;
            fillers = new ArrayList<Filler>();
        }

        public Space copy() {
            Space space = new Space(this.area);
            space.cost = this.cost;
            space.filledArea = this.filledArea;
            space.fillers.addAll(this.fillers);
            return space;
        }

        public boolean isFull() {
            return filledArea - area >= 0.0;
        }

        public void addFiller(Filler filler) {
            fillers.add(filler);
            cost += filler.getCost();
            filledArea += filler.getArea();
        }

        public double getCost() {
            return cost;
        }

        public List<Filler> getFillers() {
            ArrayList<Filler> fillersCopy = new ArrayList<Filler>();
            fillersCopy.addAll(fillers);
            return fillersCopy;
        }
    }

    protected class Filler implements Comparable<Filler> {
        private double cost;
        private double area;

        public Filler(double cost, double area) {
            this.cost = cost;
            this.area = area;
        }

        public double getArea() {
            return area;
        }

        public double getCost() {
            return cost;
        }

        @Override
        public int compareTo(Filler o) {
            int costCmp = Double.compare(cost, o.cost);
            int areaCmp = Double.compare(area, o.area);
            if(costCmp == 0 && areaCmp == 0) {
                return 0;
            }
            int combined = costCmp + areaCmp;
            if(combined == 0) {
                combined = costCmp == 0 ? areaCmp : costCmp;
            }
            return combined;
        }
    }
}
