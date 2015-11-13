package eu.dnetlib.iis.core.common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.avro.generic.GenericContainer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import eu.dnetlib.iis.core.java.io.DataStore;
import eu.dnetlib.iis.core.java.io.FileSystemPath;

/**
 * @author Łukasz Dumiszewski
 */

public class AvroTestUtils {

    
    //------------------------ LOGIC --------------------------
    
    /**
     * Reads records from avro files from local filesystem
     */
    public static <T> List<T> readLocalAvroDataStore(String outputDirPath) throws IOException {
        Path outputPath = new Path(new File(outputDirPath).getAbsolutePath());
        
        FileSystem fs = createLocalFileSystem();
        
        List<T> records = DataStore.read(new FileSystemPath(fs, outputPath));
        return records;
    }


    /**
     * Creates directory and saves in it the passed objects (in avro files).
     */
    public static <T extends GenericContainer> void createLocalAvroDataStore(List<T> records, String inputDirPath) throws IOException {
        
        File inputDir = new File(inputDirPath);
        inputDir.mkdir();
        Path inputPath = new Path(inputDir.getAbsolutePath());
        
        
        FileSystem fs = createLocalFileSystem();
        
        DataStore.create(records, new FileSystemPath(fs, inputPath));
        
    }
    
    
    //------------------------ PRIVATE --------------------------

    private static FileSystem createLocalFileSystem() throws IOException {
        Configuration conf = new Configuration();
        conf.set(FileSystem.FS_DEFAULT_NAME_KEY, FileSystem.DEFAULT_FS);
        FileSystem fs = FileSystem.get(conf);
        return fs;
    }
}