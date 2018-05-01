import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PATH {

    private static List<String> listPath;

    private static File[] file_list;
    private static List <File> find_list;

    private static File Copy_or_Move =null;


    private static void Open(int parse) {

        Scanner scanner = new Scanner(System.in);

        if (file_list[parse].isDirectory()) {
            listPath.add(file_list[parse].getName() + "\\");
            files();
        } else if (file_list[parse].isFile()) {
            boolean typeflag = true;

            FileOpen fileOpen = new FileOpen(file_list[parse]);

            for (String type : FileOpen.MusicFormatAIMP) {

                if (file_list[parse].getName().endsWith(type.toLowerCase())) {
                    try {
                        System.out.println("Выберите приложение для открытия Файла\n");

                        System.out.println("1 AIMP");
                        System.out.println("2 Windows Media Player");
                        switch (scanner.nextInt()) {
                            case 1:
                                fileOpen.open("Music", "AIMP");
                                break;
                            case 2:
                                fileOpen.open("Music", "Windows Media Player");
                                break;
                            default:
                                continue;
                        }
                        typeflag = false;
                        break;
                    } catch (IllegalArgumentException r) {
                        continue;
                    }
                }

            }

            for (String type : FileOpen.VideoFormatPotPlayer) {
                if (file_list[parse].getName().endsWith(type.toLowerCase())) {
                    try {
                        System.out.println("Выберите приложение для открытия Файла\n");

                        System.out.println("1 Pot Player");
                        System.out.println("2 Windows Media Player");
                        switch (scanner.nextInt()) {
                            case 1:
                                fileOpen.open("Video", "Pot Player");
                                break;
                            case 2:
                                fileOpen.open("Video", "Windows Media Player");
                                break;
                            default:
                                continue;
                        }
                        typeflag = false;
                        break;
                    } catch (IllegalArgumentException r) {
                        continue;
                    }
                }
            }

                for (String type : FileOpen.PicturesFormat) {
                    if (file_list[parse].getName().endsWith(type.toLowerCase())) {
                        fileOpen.open("Pictures", "");
                        typeflag = false;
                        break;
                    }
                }

                if (typeflag) {
                    fileOpen.open("Text", "");
                }

            }

        }


        private static void files () {


            if (find_list.isEmpty()) {
                file_list = FileSystem.listFiles(listPath);
            } else {
                file_list = new File[find_list.size()];
                find_list.toArray(file_list);

                System.out.printf("\n%-30s %d\n\n", "Найдено совпадений  ", file_list.length);
            }

            int count = 0;

            for (int i = 0; i < file_list.length; i++) {

                count = i + 1;

                System.out.printf("%-4d%-95s", count, file_list[i].getName());

                if (file_list[i].isFile()) {
                    System.out.printf("Размер  %10d  Mb %d B", file_list[i].length() / 1000000, file_list[i].length() % 10);
                }
                System.out.println();
            }
        }

    private static int match(String str){

        Matcher m= Pattern.compile("-").matcher(str);
        if(m.matches()){
            return 1;
        }

        Matcher g=Pattern.compile("[0-9]*").matcher(str);
        if(g.matches()){
            return 2;
        }

        return 0;
    }

    private static void pathUP(String s){


        int parse = Integer.parseInt(s) - 1;

        if (file_list.length >= parse) {

                Scanner scanner=new Scanner(System.in);

                while (true) {

                    System.out.println("\n"+file_list[parse].getName()+"\n");

                    System.out.println("1 Open");
                    System.out.println("2 Rename");
                    System.out.println("3 Copy");
                    System.out.println("4 Move");
                    System.out.println("5 Delete");
                    System.out.println("6 Properties");

                    System.out.print("\nВвод пользователя -> "); String f = scanner.next();
                    System.out.println();

                    if(match(f)==1){
                        files();
                        break;
                    }

                   else if (match(f) == 2) {

                        switch (Integer.parseInt(f)) {

                            case 1:
                                Open(parse);
                                break;

                            case 2:
                                System.out.println(FileSystem.Rename(file_list[parse])?"Success\n":"File not Rename\n");
                                break;

                            case 3:
                                Copy_or_Move =new File(file_list[parse].toString());
                                break;


                            case 4:
                                Copy_or_Move =new File(file_list[parse].toString());
                                break;

                            case 5:
                                System.out.println();
                                System.out.println(FileSystem.Delete(file_list[parse])?"Success\n":"File or Directory is not delete\n");
                                break;

                            case 6:FileSystem.Properties(file_list[parse]);
                                break;

                            default:
                                continue;

                        }

                    }
                }
            }


        }

    static void Directory() {

        listPath = new ArrayList<>();
        find_list = new ArrayList<>();
        String s;

        Scanner scanner = new Scanner(System.in);

        while (true) {

                s="";

            try {

                if (listPath.isEmpty()) {

                    System.out.println();

                    File[] file_roots = File.listRoots();

                    for (int i = 0; i < file_roots.length; i++) {
                        for (int g = i; g < file_roots.length - 1; g++) {
                            if (!file_roots[g].canRead()) {
                                File temp = file_roots[g];
                                file_roots[g] = file_roots[g + 1];
                                file_roots[g + 1] = temp;
                            }
                        }
                    }

                    for (int i = 0; i < file_roots.length; i++) {
                        int count = i + 1;
                        if (file_roots[i].canRead()) {
                            System.out.println(count + " " + file_roots[i]);
                        }
                    }

                    System.out.print("\nВвод пользователя -> ");
                    s = scanner.next().trim();

                    if (match(s) == 2) {

                        int number = Integer.parseInt(s);

                        if (file_roots.length >= number - 1) {

                            listPath.add(file_roots[number - 1].toString());

                        } else {
                            continue;
                        }
                        files();
                    }else{
                        continue;
                    }
                }

                System.out.print("\nВвод пользователя -> ");

                s = scanner.next().trim();

                if (match(s) == 1) {

                    if (!find_list.isEmpty()) {
                        find_list.clear();
                    }

                    listPath.remove(listPath.size() - 1);
                    find_list.clear();

                    if (listPath.isEmpty()) {
                        continue;
                    } else {

                        files();
                    }
                }

                if (match(s) == 2) {
                    pathUP(s);
                }

                if (s.toLowerCase().equals("n")) {

                    try {

                        System.out.println("\n1 Папка");
                        System.out.println("2 Текстовый Файл");

                        System.out.print("\nВвод пользователя -> ");
                        String name = scanner.next();
                        if (match(name) == 2) {

                            int number = Integer.parseInt(name);

                            System.out.print("Введите название Папки || Файла-> ");
                            name = scanner.next(Pattern.compile("(([A-Z]|[А-Я])|([a-z]|[а-я])|[0-9])*"));

                            switch (number) {

                                case 1:
                                    FileSystem.newDirectory(listPath, name);
                                    break;

                                case 2:
                                    FileSystem.newFile(listPath, name);
                                    break;

                                default:
                                    continue;

                            }

                            files();
                        } else {
                            continue;
                        }

                    } catch (InputMismatchException e) {
                        System.out.println("\nВ имени файла присутствуют запрещенные символы\n");
                    } catch (FileAlreadyExistsException h) {
                        System.out.println("\nПапка || Файл с таким именем уже существует\n");
                    }
                }

                if (s.equals("?")) {

                    System.out.print("\nВведите имя файла-> ");
                    find_list = FileSystem.find(file_list, scanner.next());

                    if (find_list.isEmpty()) {

                        System.out.println("\nСовпадение не найдено");
                        System.out.print("\nВвод пользователя -> ");

                        s = scanner.next();

                        if (match(s) == 1) {

                            files();
                        } else if (match(s) == 2) {
                            pathUP(s);
                        }

                    } else {
                        files();
                    }
                }

                if (s.toLowerCase().equals("p")) {
                    if (Copy_or_Move != null) {
                        if (Copy_or_Move.isFile()) {
                            System.out.println(FileSystem.Copy(listPath, Copy_or_Move) ? "\nSuccess" : "\nFile not Copy");
                        } else if (Copy_or_Move.isDirectory()) {
                            System.out.println(FileSystem.CopyDirectory(listPath, Copy_or_Move) ? "\nSuccess" : "\nDirectory not Copy");
                        }
                    }
                }

                if (s.toLowerCase().equals("m")) {
                    if (Copy_or_Move != null) {
                        if(Copy_or_Move.isFile()){
                        System.out.println(FileSystem.Move(listPath, Copy_or_Move) ? "\nSuccess" : "\nFile not Move");
                    }
                    else if(Copy_or_Move.isDirectory()){
                            System.out.println(FileSystem.MoveDirectory(listPath, Copy_or_Move) ? "\nSuccess" : "\nFile not Move");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Cannot open the file");
                e.printStackTrace();
            }
        }
    }
}
