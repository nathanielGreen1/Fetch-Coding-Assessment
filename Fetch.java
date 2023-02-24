import java.util.*;
import java.io.*;


public class Fetch {
	public static void main(String[] args){
		final String Transactions = "./" + args[1];
		try {
			BufferedReader fin = new BufferedReader(new FileReader(new File(Transactions)));

			ArrayList<String[]> payerData = new ArrayList<String[]>();
			HashMap<String, Payer> payers = new HashMap<>();

			String line = fin.readLine();
			line = fin.readLine();
			String[] payerArr = line.split(",");

			while (line != null){
				payerArr = line.split(",");
				Payer p = new Payer(payerArr[0], Integer.parseInt(payerArr[1]));
				if (payers.containsKey(payerArr[0])){
					payers.get(payerArr[0]).addBalance(p.getBalance());
				} else {
					payers.put(payerArr[0],p);
				}
				boolean flag = true;
				for (int i = 0; i < payerData.size(); i++) {
					if (earlierThan(payerArr[2],payerData.get(i)[2]) > 0){
						payerData.add(i, payerArr);
						flag = false;
						break;
					}
				}
				if (flag){
					payerData.add(payerArr);
				}
				line = fin.readLine();
			}

			int points = Integer.parseInt(args[0]);
			int i = 0;
			while (points > 0){
				if (i == payerData.size()){
					break;
				}
				payerArr = payerData.get(i);
				int transactionPoints = Integer.parseInt(payerArr[1]);
				Payer p = payers.get(payerArr[0]);

				if (points < transactionPoints){
					points = p.lowerBalance(points);
				} else {
					points = points - transactionPoints + p.lowerBalance(transactionPoints);
				}
				i++;
			}
			
			int keySize = payers.size();
			String comma = ",";
			for (String p : payers.keySet()){
				if(keySize == 1){
					comma = "";
				}
				System.out.printf("%s%s%n", payers.get(p), comma);
				keySize --;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int earlierThan(String d1, String d2){
		int d1Int = Integer.parseInt(d1.substring(1,5));
		int d2Int = Integer.parseInt(d2.substring(1,5));
		if (d1Int < d2Int){
			return 1;
		} else if (d1Int > d2Int) {
			return -1;
		}

		for (int i = 6; i <= 19; i += 3){
			d1Int = Integer.parseInt(d1.substring(i,i+2));
			d2Int = Integer.parseInt(d2.substring(i,i+2));
			if (d1Int < d2Int){
				return 1;
			} else if (d1Int > d2Int) {
				return -1;
			}
		}
		return 0;
	}
}

public class Payer{

	private String name;
	private int balance;

	public Payer(String name, int balance){
		this.name = name;
		this.balance = balance;
	}

	public int lowerBalance(int points){
		if (points >= balance){
			int temp = balance;
			balance = 0;
			return points - temp;
		} else {
			balance = balance - points;
			return 0;
		}
	}

	public void addBalance(int points){
		balance = balance + points;
	}

	public String toString(){
		return name + ": " + Integer.toString(balance); 
	}

	public boolean equals(Payer p){
		return name.equals(p.name) ? true : false;
	}

	public int getBalance(){
		return this.balance;
	}
}