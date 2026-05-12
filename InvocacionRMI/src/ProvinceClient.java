import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class ProvinceClient {

    public static void main(String[] args) {

        try {

            Registry registry =
                    LocateRegistry.getRegistry("127.0.0.1");

            IRemoteProvince rp =
                    (IRemoteProvince) registry.lookup("Province");

            Scanner sc = new Scanner(System.in);
            int opcion;
            int id;
            String shortName;
            String city;
            Province p;


            do {
                System.out.println("Seleccione una opcion:");
                System.out.println("1 --- Agregar ciudad\n" +
                                   "2 --- Modificar ciudad\n" +
                                   "3 --- Eliminar ciudad\n" +
                                   "4 --- Base de datos\n" +
                                   "5 --- Salir");

                opcion = sc.nextInt();

                switch (opcion){
                    case 1:
                        System.out.println("Ingrese los datos de la ciudad");
                        System.out.print("Id: ");
                        id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("ShortName: ");
                        shortName = sc.nextLine();

                        System.out.print("City Name: ");
                        city = sc.nextLine();

                        p = new Province(id, shortName, city);

                        rp.save(p);

                        break;

                    case 2:
                        System.out.println("Ingrese los datos de la ciudad a modificar");

                        System.out.print("Id: ");
                        id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("ShortName: ");
                        shortName = sc.nextLine();

                        System.out.print("City Name: ");
                        city = sc.nextLine();

                        p = new Province(id, shortName, city);

                        int iRet = rp.update(p);

                        break;

                    case 3:
                        System.out.println("Ingrese los datos de la ciudad a eliminar");

                        System.out.print("Id: ");
                        id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("ShortName: ");
                        shortName = sc.nextLine();

                        System.out.print("City Name: ");
                        city = sc.nextLine();

                        p = new Province(id, shortName, city);
                        rp.delete(p);

                        break;

                    case 4:
                        System.out.println("\nCities in database:\n");

                        ArrayList<Province> arrProv = rp.findAll();

                        for (Province prov : arrProv) {
                            System.out.println(prov);
                        }

                        break;

                    case 5:
                        System.out.println("Ha elegido salir");
                        break;

                }

            }while(opcion != 5);

            sc.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}