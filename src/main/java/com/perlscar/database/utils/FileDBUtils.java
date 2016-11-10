package com.perlscar.database.utils;

import com.google.common.collect.Lists;
import com.perlscar.exception.EntryNotPresentException;
import com.perlscar.exception.FileDBException;
import com.perlscar.exception.ObjectNotAccessibleException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class FileDBUtils {

    //Assuming that neither key nor value will have the pattern "-|-"
    private static final String FIELD_SEPARATOR = "-|-";

    public static boolean isPresent(File f, String entry) throws FileDBException {
        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(f);
            while (lineIterator.hasNext()) {
                if(lineIterator.nextLine().startsWith(entry)){
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileDBException(e);
        } finally {
            if(lineIterator != null) {
                lineIterator.close();
            }
        }
        return false;
    }

    public static boolean createEntry(File f, String entry) throws FileDBException {
        try {
            FileUtils.writeLines(f, Lists.newArrayList(entry), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileDBException(e);
        }
    }

    public static boolean createEntry(File f, String entry, String value) throws FileDBException {
        try {
            String data = entry + FIELD_SEPARATOR + value;
            FileUtils.writeLines(f, Lists.newArrayList(data), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileDBException(e);
        }
    }

    public static String readEntry(File f, String entry)
        throws FileDBException, EntryNotPresentException {

        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(f);
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if(line.startsWith(entry)){
                    int index = line.indexOf(FIELD_SEPARATOR);
                    return line.substring(index + FIELD_SEPARATOR.length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileDBException(e);
        } finally {
            if(lineIterator != null) {
                lineIterator.close();
            }
        }
        throw new EntryNotPresentException("Could not find entry");
    }

    public static boolean dropEntry(File f, String entry)
            throws FileDBException, EntryNotPresentException {

        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(f);
            List<String> lines = Lists.newArrayList();
            boolean found = false;
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if(line.startsWith(entry)){
                    found = true;
                    continue;
                }
                lines.add(line);
            }
            if(!found) {
                throw new EntryNotPresentException("Could not find entry");
            }
            FileUtils.writeLines(f, lines);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileDBException(e);
        } finally {
            if(lineIterator != null) {
                lineIterator.close();
            }
        }
    }

    public static List<String> getEntries(File f) throws ObjectNotAccessibleException {
        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(f);
            List<String> entries = Lists.newArrayList();
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                String entry = line.substring(0, line.indexOf(FIELD_SEPARATOR));
                entries.add(entry);
            }
            return entries;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        } finally {
            if(lineIterator != null) {
                lineIterator.close();
            }
        }
    }

}
