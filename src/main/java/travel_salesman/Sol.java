package travel_salesman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Reader.readInt;

/*
3
0 1000 5000
5000 0 1000
1000  5000  0
 */
public class Sol {
    public static void main(String[] args) {
        int citiesNum = readInt();
        List<City> cities =new ArrayList<>();

        for (int loopIndex = 0; loopIndex < citiesNum; loopIndex++) {
            City city = new City(loopIndex);
            cities.add(city);
        }
        for (int travelIndex = 0; travelIndex < citiesNum; travelIndex++) {
            City currentCity = cities.get(travelIndex);
            for (int distanceIndex = 0; distanceIndex < citiesNum; distanceIndex++) {
                City distanation = cities.get(distanceIndex);
                int cost = readInt();
                Travel travel = new Travel(distanation,cost);
                currentCity.addTravel(travel);
            }
        }
        List<City> travel = new ArrayList<>();
        travel.add(cities.get(0));
        solve(cities,travel);

        System.out.println(minOpt);
    }
    static  int minOpt = Integer.MAX_VALUE;

    static void solve(List<City> cities,List<City> travel){
        if(travel.size() == cities.size()){
            int cost = 0;
            List<City> copy = new ArrayList<>(travel);
            copy.add(cities.get(0));
//            System.out.println(copy);
            for (int i = 1; i < copy.size(); i++) {
                City previous = copy.get(i-1);
                City current = copy.get(i);
                int gain = previous.getTravelCost(current);
                cost += gain;
            }
//            System.out.println(travel);
            minOpt = Math.min(minOpt,cost);
//            System.out.println(cost);
        }
        for (int i = 0; i < cities.size(); i++) {
            City current = cities.get(i);
            if(travel.contains(current)){
                continue;
            }
            travel.add(cities.get(i));
            solve(cities, travel);
            travel.remove(current);
        }
    }
}


class City{
    private final Map<Integer,Travel> travelIndex = new HashMap<>();
    public City(int index) {
        this.index = index;
    }
    int index;

    public void addTravel(Travel travel){
        travelIndex.put(travel.destination.index,travel);
    }

    public List<Travel> getTravels(){
        return  new ArrayList<>(travelIndex.values());
    }

    public int getTravelCost(City city){
        return  travelIndex.get(city.index).cost;
    }

    @Override
    public String toString() {
        return "City{" +
                "index=" + index +
                '}';
    }
}

class Travel{
    City destination;
    int cost;

    public Travel(City destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }
}