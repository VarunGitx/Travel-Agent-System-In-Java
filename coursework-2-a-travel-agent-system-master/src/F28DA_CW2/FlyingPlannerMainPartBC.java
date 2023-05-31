package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FlyingPlannerMainPartBC {

	public static void main(String[] args) {

		FlyingPlanner fi;
		fi = new FlyingPlanner();
		try {
			fi.populate(new FlightsReader());
			Scanner scan = new Scanner(System.in);
			Set<String> airportList = fi.getAirportList();

			
			String continueStr = null;
			do {
				System.out.println("Welcome");
				String errPrint = "Invalid ,Enter Again";
				String startPrint = "Enter The Start Airport - ";
				System.out.print(startPrint);
				String startAP = scan.nextLine();
				startAP = startAP.toUpperCase();

				
				while (!airportList.contains(startAP)) {
					System.err.println(errPrint);
					System.out.print(startPrint);
					startAP = scan.nextLine();
					startAP = startAP.toUpperCase();
				}

				String desPrint = "Enter Destination Airport - ";
				System.out.print(desPrint);
				String desAP = scan.nextLine();
				desAP = desAP.toUpperCase();

				while (!airportList.contains(desAP)) {
					System.err.println(errPrint);
					System.out.print(desPrint);
					desAP = scan.nextLine();
					desAP = desAP.toUpperCase();
				}

				String continuePrint = "Do You Want To Try Again? Type Y To Indicate Yes";
				String excludePrint = "Enter Airports Which Are Needed To be excluded ,Type Y To Indicate Yes";

				System.out.println();
				System.out.println("Select Below - ");
				System.out.println("1 - Journey Search (Type - 1)");
				System.out.println("2 - Meet-Up Search (Type - 2)");
				int search = scan.nextInt();

				switch (search) {
				case 1:
					System.out.println();
					System.out.println("Select Journey of Desire - ");
					System.out.println("1 - Least Cost ");
					System.out.println("2 - Least Cost (leaving out a few airports)");
					System.out.println("3 - Least Changeovers ");
					System.out.println("4 - Least Changeovers (leaving out a few airports)");
					System.out.println("5 - Least Time");
					int preference = scan.nextInt();

					switch (preference) {
					
					
					case 1:
						Journey jlc = fi.leastCost(startAP, desAP);
						jlc.printJourney();
						System.out.println();
						System.out.println(continuePrint);
						scan.nextLine();
						continueStr = scan.nextLine();
						break;
						
						
					case 2:
						
						List<String> excludingC = new LinkedList<String>();
						System.out.println(excludePrint);
						String exAPC;
						do {
							exAPC = scan.nextLine();
							exAPC = exAPC.toUpperCase();
							excludingC.add(exAPC);
						} while (!exAPC.equalsIgnoreCase("n"));
						
						
						Journey jlce = fi.leastCost(startAP, desAP, excludingC);
						jlce.printJourney();
						System.out.println();
						System.out.println(continuePrint);
						continueStr = scan.nextLine();
						break;
						
						
					case 3:
						
						Journey jlh = fi.leastHop(startAP, desAP);
						jlh.printJourney();
						System.out.println();
						System.out.println(continuePrint);
						scan.nextLine();
						continueStr = scan.nextLine();
						break;
						
						
					case 4:
						
						List<String> excludingH = new LinkedList<String>();
						System.out.println(excludePrint);
						String exAPH;
						do {
							exAPH = scan.nextLine();
							exAPH = exAPH.toUpperCase();
							excludingH.add(exAPH);
						} while (!exAPH.equalsIgnoreCase("n"));

						Journey jlhe = fi.leastHop(startAP, desAP, excludingH);
						jlhe.printJourney();
						System.out.println();
						System.out.println(continuePrint);
						continueStr = scan.nextLine();
						break;
						
						
					case 5:
						
						System.out.println("Max num stops (2-5): ");
						int maxStop = scan.nextInt();
						Journey jlt = fi.leastTime(startAP, desAP, maxStop);
						jlt.printJourney();
						System.out.println();
						System.out.println(continuePrint);
						scan.nextLine();
						continueStr = scan.nextLine();
						break;}
					break;
					
				case 2:
					System.out.println();
					System.out.println("Select Search of Desire ");
					System.out.println("1 - Least Cost");
					System.out.println("2 - Least Hops");
					System.out.println("3 - Least Journey Time");
					int number = scan.nextInt();
					
					break;
					
				}

			} while (continueStr.equalsIgnoreCase("y"));
			System.out.println("Thank You For Using Flying Planner ");

		} catch (FileNotFoundException | FlyingPlannerException e) {
			e.printStackTrace();
		}

	}
}

