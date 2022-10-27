package interfaces;

import java.util.Objects;

// records: (GA in Java 16)
// - are immutable
// - autogenerate toString, equals, and hashCode
// - have "getters" that match the names of the fields (first(), last())
public final class CompanyEmployee implements Company, Employee {
    private final String first;
    private final String last;

    public CompanyEmployee(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String getName() {
        return Employee.super.getName() + " works for " +
                Company.super.getName();
    }

    @Override
    public void doWork() {
        System.out.println("Converting caffeine into code for money...");
    }

    @Override
    public String first() {
        return first;
    }

    @Override
    public String last() {
        return last;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CompanyEmployee) obj;
        return Objects.equals(this.first, that.first) &&
                Objects.equals(this.last, that.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }

    @Override
    public String toString() {
        return "CompanyEmployee[" +
                "first=" + first + ", " +
                "last=" + last + ']';
    }


}
