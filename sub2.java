import java.io.*;
public class sub2
{
    public static void main(String args[])
        {
            File f[]=new File("D:\\lectures\\prECALCULS\\done").listFiles();
            File g[]=new File("D:\\lectures\\prECALCULS\\New folder").listFiles();
            for(int i=0;i<f.length;i++)
            {
                String tmp=f[i].getName();
                String in=tmp.substring(0,tmp.indexOf(".")+1);
                for(int y=0;y<g.length;y++)
                {
                    String tmpp=g[y].getName();
                    print(in);
                    if(tmp.substring(tmp.indexOf(".")+1,tmp.lastIndexOf(".")).equals(tmpp.substring(0,tmpp.lastIndexOf("."))))
                    {
                        System.out.println(true);
                        print(tmp.substring(tmp.indexOf(".")+1,tmp.lastIndexOf(".")));
                        print(tmpp.substring(0,tmpp.lastIndexOf(".")));
                        g[y].renameTo(new File("D:\\lectures\\prECALCULS\\New folder\\"+in+g[y].getName()));
                    }
                }
            }
            }   
            public static void print(String as)
            {
                System.out.println(as);
            }
        }

