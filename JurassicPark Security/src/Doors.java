public class Doors {

	private static boolean[] building1;
	private static boolean[] building2;
	private static boolean[] building3;
	private static boolean[] building4;
	private static boolean[] building5;
	private static boolean doorStatus;

	public Doors(int buildingNumber) { // default constructor - creates a building array for all of the buildings
										// with all doors set to false (Locked) by default
		if (buildingNumber == 1) { // building 1 initializer
			building1 = new boolean[10];
			for (int d = 0; d < building1.length; d++) {
				building1[d] = false;
			}
		} else if (buildingNumber == 2) { // building 2 initializer
			building2 = new boolean[10];
			for (int d = 0; d < building2.length; d++) {
				building2[d] = false;
			}
		} else if (buildingNumber == 3) { // building 3 initializer
			building3 = new boolean[10];
			for (int d = 0; d < building3.length; d++) {
				building3[d] = false;
			}
		} else if (buildingNumber == 4) { // building 4 initializer
			building4 = new boolean[10];
			for (int d = 0; d < building4.length; d++) {
				building4[d] = false;
			}
		} else if (buildingNumber == 5) { // building 4 initializer
			building5 = new boolean[10];
			for (int d = 0; d < building5.length; d++) {
				building5[d] = false;
			}
		}

	}// Buildings Setup ends

	public static boolean getDoorStatus(int buildingNumber, int doorNumber) { // accesses the status of the door
		doorNumber--;
		if (buildingNumber == 1) {
			doorStatus = building1[doorNumber];
		} else if (buildingNumber == 2) {
			doorStatus = building2[doorNumber];
		} else if (buildingNumber == 3) {
			doorStatus = building3[doorNumber];
		} else if (buildingNumber == 4) {
			doorStatus = building4[doorNumber];
		} else if (buildingNumber == 5) {
			doorStatus = building5[doorNumber];
		}
		return doorStatus;
	}

	public static boolean setDoorStatus(int buildingNumber, int doorNumber) { // Locks or unlocks the entered door
		doorNumber--; // only 10 elements in the building array, therefore subtracted the doorNumber
						// to avoid
						// out of bounds error and match with the array indices

		if (buildingNumber == 1) {
			if (building1[doorNumber] == false) {
				building1[doorNumber] = true;
				doorStatus = building1[doorNumber];
			} else if (building1[doorNumber] == true) {
				building1[doorNumber] = false;
				doorStatus = building1[doorNumber];
			}
		} else if (buildingNumber == 2) {
			if (building2[doorNumber] == false) {
				building2[doorNumber] = true;
				doorStatus = building2[doorNumber];
			} else if (building2[doorNumber] == true) {
				building2[doorNumber] = false;
				doorStatus = building2[doorNumber];
			}
		} else if (buildingNumber == 3) {
			if (building3[doorNumber] == false) {
				building3[doorNumber] = true;
				doorStatus = building3[doorNumber];
			} else if (building3[doorNumber] == true) {
				building3[doorNumber] = false;
				doorStatus = building3[doorNumber];
			}
		} else if (buildingNumber == 4) {
			if (building4[doorNumber] == false) {
				building4[doorNumber] = true;
				doorStatus = building4[doorNumber];
			} else if (building4[doorNumber] == true) {
				building4[doorNumber] = false;
				doorStatus = building4[doorNumber];
			}
		} else if (buildingNumber == 5) {
			if (building5[doorNumber] == false) {
				building5[doorNumber] = true;
				doorStatus = building5[doorNumber];
			} else if (building5[doorNumber] == true) {
				building5[doorNumber] = false;
				doorStatus = building5[doorNumber];
			}
		}
		return doorStatus;
	}
}