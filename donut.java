public class DonutAnimation {
    public static void main(String[] args) throws InterruptedException {
        double A = 0, B = 0;
        int width = 80;
        int height = 22;
        String chars = ".,-~:;=!*#$@";
        
        while (true) {
            StringBuilder screen = new StringBuilder();
            double[] zbuffer = new double[width * height];
            char[] buffer = new char[width * height];
            
            java.util.Arrays.fill(buffer, ' ');
            java.util.Arrays.fill(zbuffer, 0);
            
            for (int j = 0; j < 628; j += 7) {
                double cosTheta = Math.cos(j * 0.01);
                double sinTheta = Math.sin(j * 0.01);
                
                for (int i = 0; i < 628; i += 2) {
                    double sinPhi = Math.sin(i * 0.01);
                    double cosPhi = Math.cos(i * 0.01);
                    
                    double sinA = Math.sin(A);
                    double cosA = Math.cos(A);
                    double sinB = Math.sin(B);
                    double cosB = Math.cos(B);
                    
                    double h = cosTheta + 2;
                    double D = 1 / (sinPhi * h * sinA + sinTheta * cosA + 5);
                    double t = sinPhi * h * cosA - sinTheta * sinA;
                    
                    int x = (int)(40 + 30 * D * (cosPhi * h * cosB - t * sinB));
                    int y = (int)(12 + 15 * D * (cosPhi * h * sinB + t * cosB));
                    int idx = x + width * y;
                    
                    if (y > 0 && y < height && x > 0 && x < width && D > zbuffer[idx]) {
                        zbuffer[idx] = D;
                        
                        double luminance = (sinTheta * sinA - sinPhi * cosTheta * cosA) * cosB;
                        luminance -= sinPhi * cosTheta * sinA + sinTheta * cosA;
                        luminance -= cosPhi * cosTheta * sinB;
                        
                        int N = (int)(8 * luminance);
                        buffer[idx] = chars.charAt(Math.max(0, Math.min(N, chars.length() - 1)));
                    }
                }
            }
            
            clearScreen();
            for (int i = 0; i < height; i++) {
                screen.append(buffer, i * width, width).append('\n');
            }
            System.out.print(screen);
            
            A += 0.04;
            B += 0.02;
            Thread.sleep(30);
        }
    }
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}