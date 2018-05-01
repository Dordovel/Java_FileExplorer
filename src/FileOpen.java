import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileOpen {

    static final String MusicFormatAIMP[]={".CDA", ".AAC", ".AC3", ".APE", ".DTS", ".FLAC", ".IT", ".MIDI", ".MO3", ".MOD", ".M4A", ".M4B", ".MP1", ".MP2", ".MP3", ".MPC", ".MTM", ".OFR", ".OGG", ".RMI", ".S3M", ".SPX", ".TAK", ".TTA", ".UMX", ".WAV", ".WMA", ".WV", ".XM"};

    static final String VideoFormatPotPlayer[]={"3G2", "3GP", "3GP2", "3GPP", "AMV", "ASF", "ASX", "AVI", "CUE", "DAT", "DIVX", "DPG", "DPL", "DTSHD", "DVR-MS", "EAC3", "EVO", "FLV", "IFO", "K3G", "MPEG-1", "M1V", "M2A", "M2T", "M2TS", "M2V", "M3U", "M4P", "M4V", "MKA", "MKV", "MOV", "MP2V", "MP4", "MPA", "MPE", "MPG", "MPL", "MPLS", "MPV2", "MTS", "NSR", "NSV", "OPUS", "PLS", "QT", "RA", "RAM", "RM", "RMVB", "RPM", "SKM", "SWF", "TP", "TPR", "TRP", "TS", "VOB", "WAX", "WEBM", "WM", "WMP", "WMV", "WMX", "WVX"};

    static final String PicturesFormat[]={".JPG",".PNG",".BMP",".GIF",".JPEG"};

    private static File file;

    FileOpen(File file){

        this.file=file;

    }


    public void open(String type,String player){


            try{
                if(type.equals("Music")) {
                    if (player.equals("AIMP")) {
                        new ProcessBuilder("C:\\Program Files (x86)\\AIMP\\AIMP.exe\\", file.toString()).start();

                        System.out.println("\nFile    " + file.getName() + " is Open\n");
                    }
                    else if(player.equals("Windows Media Player")){
                        new ProcessBuilder("C:\\Program Files\\Windows Media Player\\wmplayer.exe\\", file.toString()).start();

                        System.out.println("\nFile    " + file.getName() + " is Open\n");
                    }
                }

                if(type.equals("Text")){
                    new ProcessBuilder("notepad.exe\\", file.toString()).start();

                    System.out.println("\nFile    " + file.getName() + " is Open\n");
                }

                if(type.equals("Video")) {
                    if (player.equals("Pot Player")) {
                        new ProcessBuilder("C:\\Program Files (x86)\\Daum\\PotPlayer\\PotPlayerMini.exe\\", file.toString()).start();

                        System.out.println("\nFile    " + file.getName() + " is Open\n");
                    }
                    else if (player.equals("Windows Media Player")) {
                        new ProcessBuilder("C:\\Program Files\\Windows Media Player\\wmplayer.exe\\", file.toString()).start();

                        System.out.println("\nFile    " + file.getName() + " is Open\n");
                    }
                }

                if(type.equals("Pictures")){
                    Runtime.getRuntime().exec("rundll32.exe C:\\WINDOWS\\System32\\shimgvw.dll,ImageView_Fullscreen "+file.toString());
                }
    }catch (IOException e){
                System.out.println("Cannot open the file");
            }
    }
}
