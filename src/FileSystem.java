import org.jd3lib.MP3File;
import org.jd3lib.MetaData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileSystem {

    private static int size=0;

    private static List<File> find_list;

    static File[] listFiles(List<String> list){

            File file=new File(path(list));
            System.out.printf("%90s\n",file.getName());

        return sort(file.listFiles());
    }

    private static String path(List<String> list){

        String path="";

        Iterator<String> iterator=list.iterator();

        while (iterator.hasNext()){

            path+=iterator.next();

        }

        return  path;
    }

   private static File[] sort (File [] file_list){

        for(int i=0;i<file_list.length;i++){
            for(int g=file_list.length-1;g>i;g--){

                if(file_list[g].isDirectory()){

                    File temp=file_list[g];

                    file_list[g]=file_list[g-1];

                    file_list[g-1]=temp;

                }

            }
        }
        return file_list;
    }

    static void Properties(File file){

        for(int i=0;i<180;i++){
            System.out.print("-");
        }

        System.out.printf("\nName  %172s |\n\n",file.getName());

            if(file.getName().endsWith(".mp3")){
                MP3File mp3File=new MP3File(file);
                MetaData metaData=mp3File.getMetaData();

                System.out.println("Название:    "+metaData.getTitle().replace("ÿþ",""));
                System.out.println("Исполнитель:    "+metaData.getArtist().replace("ÿþ",""));
                System.out.println("Альбом:    "+metaData.getAlbum().replace("ÿþ",""));
                System.out.println("Год:    "+metaData.getYear()+"\n");

                if(metaData.getGenre()!=null){
                    System.out.println("Жанр:    "+metaData.getGenre()+"\n");
                }
            }

        System.out.printf("Size  %170d MB|\n", Size(file));

        if(file.canRead()) {

            System.out.printf("Read  %173s|", " Ok ");
        }

        if(file.canWrite()) {

            System.out.printf("\nWrite  %172s|\n", " Ok ");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy 'at' HH:mm");
        System.out.printf("Last Modified %164s |\n",dateFormat.format( new Date(file.lastModified()) ) );


        for(int i=0;i<180;i++){
            System.out.print("-");
        }

        System.out.println();
    }

    static boolean Delete(File file){

        if(file.isDirectory()) {
            System.out.println("Directory");

            File[] file_array = file.listFiles();

                for (File f : file_array) {

                    if (f.isDirectory()) {
                        Delete(f);
                    }

                    f.delete();

                }

            return true;
            }

            if(file.isFile()){
            file.delete();
            return true;
            }

        return false;
    }

    static boolean Rename(File file){

        boolean flag=false;

        System.out.print("-> ");

        if(file.isDirectory()) {

          flag =  file.renameTo(new File(file.getParent()+new Scanner(System.in).nextLine()));

        }

        if (file.isFile()){

            String format=file.getName().substring(file.getName().indexOf("."));

            flag = file.renameTo(new File(file.getParent()+new Scanner(System.in).nextLine()+format));
        }
        return flag;
    }

    static boolean Copy(List<String> Path,File file){



        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = new FileOutputStream(new File(path(Path)+file.getName()));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);

            }

            return true;
        }catch(Exception e){
            return false;
            }
    }

    static boolean Move(List<String> Path,File file){

       return file.renameTo(new File(path(Path)+file.getName()));

    }

    private static int Size(File file){

        File[] file_array=file.listFiles();

        try{
            if(file.isDirectory()) {
                for (File f : file_array) {

                    if (f.isDirectory()) {
                        Size(f);
                    }

                    size += f.length() / 1000000;

                }
                return size;
            }

            else {
                return (int)file.length()/1000000;
            }
        }catch (NullPointerException e) {
            return 0;
        }
    }

    static List<File> find(File file_array[],String Name){

        if(find_list==null){
            find_list=new ArrayList<>();
        }


        for(int i=0;i<file_array.length;i++) {
            if (file_array[i].isFile()) {
                if (file_array[i].getName().toLowerCase().contains(Name.toLowerCase())) {
                    find_list.add(file_array[i]);
                }
            }
        }

        return find_list;

    }

    static boolean CopyDirectory(List<String> Path,File file)  {

    try {

            newDirectory(Path, file.getName());

            File temp[] = new File(file.toString()).listFiles();

            Path.add(file.getName()+"\\");

            for(File f:temp){
                Copy(Path,f);
            }

            return true;

        }catch (IOException e){
            return  false;
        }
    }

    public static boolean MoveDirectory(List<String> Path,File file){
        try {
            newDirectory(Path, file.getName());

            File temp[] = new File(file.toString()).listFiles();

            Path.add(file.getName()+"\\");

            for(File f:temp){
                Move(Path,f);
            }

            file.delete();

            return true;

        }catch (IOException e){
            return  false;
        }
    }

    public static void newDirectory(List<String> list, String Name) throws InputMismatchException,IOException{

        if(!Name.equals("")) {
            Files.createDirectory(Paths.get(path(list).trim()+Name));

        }
    }

    public static void newFile(List<String> list, String Name)throws InputMismatchException,IOException{

        if(!Name.equals("")) {
            Files.createFile(Paths.get(path(list).trim()+Name+".txt"));

        }

    }


}
