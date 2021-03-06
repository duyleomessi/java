import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class ResultCalculator {
	
	public static double getResult(String teamDir) throws FileNotFoundException {
		String pathFileJury = PathHelper.pathJuryDir()+File.separator+"jury.txt";
		return getResult(pathFileJury,teamDir);
	}
//	public static void main(String[] args) {
//		double result;
//		result = getResult("output.txt", "output_candidate.txt");
//		System.out.println("result: " +  result);
//	}
	
	
	static double getResult(String juryDir, String teamDir) {
        final int maxLength = 1 << 50;
        Scanner juryFile, teamFile;
        try {
            File fileJury = new File(juryDir);
            File fileTeam = new File(teamDir);
        	
            if (fileJury.length() > maxLength || fileTeam.length() > maxLength)
                return -1.0; // File should not have more than 2^50 characters.
            juryFile = new Scanner(fileJury);
            teamFile = new Scanner(fileTeam);
        } catch (Exception e) {
            return -1.0;
        }
        Frame nJury = getNFrame(juryFile);
        Frame nTeam = getNFrame(teamFile);
        
        if (nJury == null || nTeam == null) {
        	return -1.0; 
        }
//        if (nJury.n != nTeam.n) {
//        	return -1.0;
//        }
        HashMap<Integer, TPoint> jury = getCoordinates(juryFile, nJury);
        HashMap<Integer, TPoint> team = getCoordinates(teamFile, nTeam);
        juryFile.close();
        teamFile.close();
        return getDiff(jury, team, nJury.n);
    }
    
    private static Frame getNFrame(Scanner in) {
        int n;
        try {
            n = in.nextInt();
        } catch (Exception e) {
            return null;
        }
        return new Frame(n);
    }
    private static HashMap<Integer, TPoint> getCoordinates(Scanner in, Frame frame){
        HashMap<Integer, TPoint> c = new HashMap<Integer, TPoint>();
        while (in.hasNext()) {
            int id;
            int signalCode;
            try {
                id = in.nextInt(); // number of line
                if (id < 0 ) return null;
                signalCode = in.nextInt();
                if (signalCode < 1 || signalCode > 8) return null;
            } catch (Exception e) {
                return null;
            }
            
            try {
                double tmpX1 = in.nextDouble();
                double tmpY1 = in.nextDouble();
                double tmpX2 = in.nextDouble();
                double tmpY2 = in.nextDouble();
                if (c.containsKey(id)) return null;
                c.put(id, new TPoint(signalCode, tmpX1, tmpY1, tmpX2, tmpY2));
            } catch (Exception e) {
                return null;
            }
        }
        return c;
    }

    /* Return difference between 2 point sets.
    *  Return -1 if input is invalid.
    *  getDiff(jury, team) may not equal to getDiff(team, jury)*/
    private static double getDiff(HashMap<Integer, TPoint> jury, HashMap<Integer, TPoint> team, int n) {
        if (jury == null || team == null) return -1.0;
        double score = 0.0;
        boolean intersection;
        double commonArea;
        
        for(int id : jury.keySet()) {
        	TPoint tmp = new TPoint(0,0.0, 0.0, 0.0, 0.0);
            if (team.containsKey(id)) {
            	tmp = team.get(id);
            }
            if (!jury.get(id).checkSignalCode(tmp)) {
            	score += 0;
            } else {
            	if (tmp.x1 > jury.get(id).x1) {
                	intersection =  jury.get(id).checkIfIntersection(tmp);
                	if (!intersection) {
                		score += 0;
                	} else {
                		commonArea = jury.get(id).getAreasOfCommonArea(tmp);
                		score += (commonArea) / (jury.get(id).getAreas() + tmp.getAreas() - commonArea);
                	}
                } else {
                	intersection =  tmp.checkIfIntersection(jury.get(id));
                	if (!intersection) {
                		score += 0;
                	} else {
                		commonArea = tmp.getAreasOfCommonArea(jury.get(id));
                		score += (commonArea) / (jury.get(id).getAreas() + tmp.getAreas() - commonArea);
                	}
                }
            }
            	
            //score += jury.get(id).getAreasOfCommonArea(tmp);
            
        }
        return score / n;
    }

    private static class TPoint {
    	int signalCode;
        double x1, y1, x2, y2;
        double d, h;
        TPoint(){}
        TPoint(int signalCode, double vx1, double vy1, double vx2, double vy2) {
        	this.signalCode = signalCode;
        	x1 = vx1;
        	y1 = vy1; 
        	x2 = vx2; 
        	y2 = vy2;
        }
//        private double distance(TPoint other) {
//            return Math.hypot(x - other.x, y - other.y);
//        }
        private boolean checkSignalCode(TPoint other) {
        	if(other.signalCode == signalCode) {
        		return true;
        	} 
        	System.out.println("The signal is incorrect");
        	return false;
        }
        
        private boolean checkIfIntersection(TPoint other){
        	if(other.y2 < y1 || x2 < other.x1 || y2 < other.y1) {
        		System.out.println("Not intersection");
        		return false;
        	} 
        	return true;
        }
        
        private double getAreas() {
        	System.out.println("Areas is: " + (x2 - x1 + 1) * (y2 - y1 + 1));
        	return (x2 - x1 + 1) * (y2 - y1 + 1);
        } 
        
        private double getAreasOfCommonArea(TPoint other) {
        	d  = x2 - other.x1 + 1;
        	if( (other.y1 <= y1) && (other.y2 <= y2)) {
        		h = other.y2 - y1 + 1;
        	} else if((y1 <= other.y1) && (other.y2 <= y2)) {
        		h = other.y2 - other.y1 + 1;
        	} else if ((y1 <= other.y1) && (y2 <= other.y2)) {
        		h = y2 - other.y1 + 1;
        	}
        	System.out.println("Common areas is: " + d * h);
        	return (d * h);
        }
    }
    
    private static class Frame {
        int n;
        Frame(){}
        Frame(int sz){n = sz;}
    }

}
