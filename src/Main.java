import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Main {

    public static void main(String[] args) {

        String scelta = "", gruppoSanguigno = "", fattoreRH = "", sequenza = "";
        int sceltaInt = 0;

        try {
            String connURL = "jdbc:sqlserver://213.140.22.237\\SQLEXPRESS:1433;databaseName=gottardo.joshua;user=gottardo.joshua;password=xxx123#";
            Connection connection = DriverManager.getConnection(connURL);

            do {
                Scanner scan = new Scanner(System.in);
                System.out.println("");
                System.out.println("1: ELENCO DONATORI");
                System.out.println("2: ELENCO ESAMI");
                System.out.println("3: NUMERO MEDICI");
                System.out.println("4: ELIMINAZIONE DONATORI");
                System.out.println("5: ESCI");
                scelta = scan.nextLine();
                sceltaInt = Integer.parseInt(scelta);

                switch (sceltaInt) {
                    case 1:
                        System.out.println("Inserisci gruppo sanguigno");
                        gruppoSanguigno = scan.nextLine();
                        gruppoSanguigno = gruppoSanguigno.toUpperCase();
                        System.out.println("Inserisci il fattore RH");
                        fattoreRH = scan.nextLine();
                        gruppoSanguigno = gruppoSanguigno.concat(fattoreRH);
                        String sql1 = "SELECT Cognome, Nome, Telefono FROM PSDonatore WHERE GruppoSanguigno = ? ORDER BY Cognome ASC";
                        PreparedStatement stmt1 = connection.prepareStatement(sql1);
                        stmt1.setString(1, gruppoSanguigno);
                        ResultSet rs1 = stmt1.executeQuery();
                        while (rs1.next()) {
                            System.out.println("");
                            System.out.print(rs1.getString("Nome") + " ");
                            System.out.print(rs1.getString("Cognome") + " ");
                            System.out.print(rs1.getString("Telefono") + "\n");
                        }
                        break;

                    case 2:
                        System.out.println("Inserisci sequenza di lettere");
                        sequenza = scan.nextLine();
                        sequenza = sequenza.concat("%");
                        String sql2 = "SELECT Nome, ValMin, ValMax, Unita, Metodo FROM PSEsame WHERE Nome LIKE ?";
                        PreparedStatement stmt2 = connection.prepareStatement(sql2);
                        stmt2.setString(1, sequenza);
                        ResultSet rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            System.out.println("");
                            System.out.print(rs2.getString("Nome") + " ");
                            System.out.print(" | Min: " + rs2.getInt("ValMin") + " ");
                            System.out.print(" | Max: " + rs2.getInt("ValMax") + " ");
                            System.out.print(" | Unità: " + rs2.getString("Unita") + " ");
                            System.out.print(" | Metodo: " + rs2.getString("Metodo") + " ");
                        }

                        break;

                    case 3:
                        Statement stmt3 = connection.createStatement();
                        String sql3 = "SELECT COUNT(idDottore) as dottori FROM PSDottore";
                        ResultSet rs3 = stmt3.executeQuery(sql3);
                        while (rs3.next()) {
                            System.out.println("\nCi sono " + rs3.getInt("dottori") + " dottori");
                        }


                        break;

                    case 4:
                        Statement stmt4 = connection.createStatement();
                        String sql4 = "DELETE FROM PSDonatore WHERE DATEDIFF(YEAR, DataDiNascita, GETDATE()) >= 60";
                        stmt4.executeUpdate(sql4);
                        System.out.println("\nOperazione eseguita correttamente");
                        break;

                    case 5:
                        break;

                    default:
                        System.out.println("ERRORE: scelta non esistente");
                }
            } while (sceltaInt != 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
