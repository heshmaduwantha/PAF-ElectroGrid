package utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

public class RandomID {
	
	public int getuniqueid(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		return (int) timestamp.getTime();
	}
}
