import java.io.*;
public class sub
{
    public static void main(String args[])
        {
            File f[]=new File("D:\\lectures\\prECALCULS\\New folder").listFiles();
            for(int i=0;i<f.length;i++)
            {
                String a=f[i].getName();
                f[i].renameTo(new File("D:\\lectures\\prECALCULS\\New folder"+(a.substring(14,a.length()))));
            }            
        }
}
