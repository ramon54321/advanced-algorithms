package Lab6;

import java.util.Comparator;

public class KnapsackItem {
    public float weight;
    public float value;

    public KnapsackItem(float value, float weight) {
        this.weight = weight;
        this.value = value;
    }

    public float getDensity() {
        return value / weight;
    }

    public static Comparator<KnapsackItem> ValueDensityComparator = new Comparator<KnapsackItem>() {
        public int compare(KnapsackItem i1, KnapsackItem i2) {
            float density1 = i1.value / i1.weight;
            float density2 = i2.value / i2.weight;
            if(density1 > density2) {
                return -1;
            } else {
                return 1;
            }
        }
    };
}
