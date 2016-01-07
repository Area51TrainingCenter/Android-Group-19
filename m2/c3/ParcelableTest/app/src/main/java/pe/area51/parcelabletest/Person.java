package pe.area51.parcelabletest;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private final String firstName;
    private final String lastName;
    private final int age;

    /*
    El Parcel debe leerse en el mismo orden en
    que se escribió.
     */
    public Person(Parcel source) {
        this.firstName = source.readString();
        this.lastName = source.readString();
        this.age = source.readInt();
    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    /*
    Este método debe devolver "0" o "1" (constante
    "Parcelable.CONTENTS_FILE_DESCRIPTOR"). Se utiliza la constante
    "CONTENTS_FILE_DESCRIPTOR" (o 1), cuando se implementa la interfaz
    "Parcelable" con la clase "FileDescriptor". En todos los otros casos,
    debe devolverse 0.
    */
    @Override
    public int describeContents() {
        return 0;
    }

    /*
    Debe considerarse que posteriormente para leer el parcel
    se debe leer en este mismo orden.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(age);
    }

    /*
    El objeto "Creator" es necesario para recrear nuestro objeto
    a partir de un "Parcel". Debe crearse como público y estático.
    */
    public static Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
