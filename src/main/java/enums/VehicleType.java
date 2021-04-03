package enums;

import exceptions.VehicleTypeException;

import java.util.Locale;

public enum VehicleType {
    CAR,VAN,MOTORCYCLE,TRUCK ;

    public static VehicleType formValue(String vehicleType) throws Exception{
        switch (vehicleType.toLowerCase(Locale.ROOT)){
            case "car" :
                return VehicleType.CAR;
            case "van" :
                return VehicleType.VAN;
            case "motorcycle" :
                return VehicleType.MOTORCYCLE;
            case "truck" :
                return VehicleType.TRUCK;
            default:
                throw new VehicleTypeException("Vehicle Type is invalid !");
        }
    }
}
