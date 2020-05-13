package com.rl.solenis.db.migration.utility;

import java.io.File;
import org.apache.log4j.Logger;

public class FileRemover
{
    private String directory;
    private static final Logger logger;
    
    static {
        logger = Logger.getLogger(FileRemover.class);
    }
    
    public FileRemover(final String directory) {
        this.directory = directory;
    }
    
    public boolean purgeFiles() {
        final File folder = new File(this.directory);
        final File[] listOfFiles = folder.listFiles();
        try {
            File[] array;
            for (int length = (array = listOfFiles).length, i = 0; i < length; ++i) {
                final File file = array[i];
                if (file.isFile()) {
                    logger.debug(String.valueOf(file.getName()) + " file Removed ");
                    file.delete();
                }
            }
        }
        catch (Exception e) {
            logger.error("Unable to delete file...", e);
            return false;
        }
        return true;
    }
}
