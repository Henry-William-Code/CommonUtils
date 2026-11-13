package com.henry.common.utils.file;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileUtilTest {

    @Test
    public void getExtension_usesLastDotInFileNameOnly() {
        assertEquals("", FileUtil.getExtension(null));
        assertEquals("", FileUtil.getExtension(""));
        assertEquals("", FileUtil.getExtension(" \t"));
        assertEquals("", FileUtil.getExtension("file"));
        assertEquals("", FileUtil.getExtension("file."));
        assertEquals("", FileUtil.getExtension(".gitignore"));
        assertEquals("", FileUtil.getExtension(".."));
        assertEquals("txt", FileUtil.getExtension("file.txt"));
        assertEquals("TXT", FileUtil.getExtension("file.TXT"));
        assertEquals("gz", FileUtil.getExtension("archive.tar.gz"));
        assertEquals("txt", FileUtil.getExtension("/path/to/file.txt"));
        assertEquals("", FileUtil.getExtension("/path/to/file"));
        assertEquals("", FileUtil.getExtension("dir.old/file"));
        assertEquals("", FileUtil.getExtension("a.b/c"));
        assertEquals("txt", FileUtil.getExtension("C:\\foo\\bar\\a.txt"));
        assertEquals("txt", FileUtil.getExtension("a/b\\c.txt"));
        assertEquals("bak", FileUtil.getExtension(".gitignore.bak"));
        assertEquals("", FileUtil.getExtension("foo/bar/"));
        assertEquals("", FileUtil.getExtension("/"));
    }

    @Test
    public void getName_keepsExtensionAndDropsDirectory() {
        assertEquals("", FileUtil.getName(null));
        assertEquals("", FileUtil.getName(""));
        assertEquals("", FileUtil.getName(" \t"));
        assertEquals("file.txt", FileUtil.getName("file.txt"));
        assertEquals("file.txt", FileUtil.getName("/path/to/file.txt"));
        assertEquals("a.txt", FileUtil.getName("C:\\foo\\a.txt"));
        assertEquals("c.txt", FileUtil.getName("a/b\\c.txt"));
        assertEquals(".gitignore", FileUtil.getName("dir/.gitignore"));
        assertEquals("file.", FileUtil.getName("dir.old/file."));
        assertEquals("", FileUtil.getName("foo/bar/"));
        assertEquals("", FileUtil.getName("/"));
        assertEquals("archive.tar.gz", FileUtil.getName("archive.tar.gz"));
    }

    @Test
    public void getNameWithoutExtension_keepsNamesThatHaveNoExtension() {
        assertEquals("", FileUtil.getNameWithoutExtension(null));
        assertEquals("", FileUtil.getNameWithoutExtension("  "));
        assertEquals("file", FileUtil.getNameWithoutExtension("file.txt"));
        assertEquals("archive.tar", FileUtil.getNameWithoutExtension("/path/to/archive.tar.gz"));
        assertEquals("file", FileUtil.getNameWithoutExtension("dir.old/file"));
        assertEquals("file.", FileUtil.getNameWithoutExtension("file."));
        assertEquals(".gitignore", FileUtil.getNameWithoutExtension(".gitignore"));
        assertEquals(".gitignore", FileUtil.getNameWithoutExtension("dir/.gitignore.bak"));
        assertEquals("..", FileUtil.getNameWithoutExtension(".."));
        assertEquals("", FileUtil.getNameWithoutExtension("foo/bar/"));
        assertEquals("a", FileUtil.getNameWithoutExtension("C:\\foo\\a.txt"));
    }

    @Test
    public void isEmptyAndSize_onlyZeroLengthFilesCountAsEmpty() throws Exception {
        assertTrue(FileUtil.isEmpty(null));
        assertEquals(0L, FileUtil.size(null));

        File dir = newTempDir();
        try {
            File missing = new File(dir, "missing.txt");
            assertTrue(FileUtil.isEmpty(missing));
            assertEquals(0L, FileUtil.size(missing));

            File empty = new File(dir, "empty.txt");
            assertTrue(empty.createNewFile());
            assertTrue(FileUtil.isEmpty(empty));
            assertEquals(0L, FileUtil.size(empty));

            File filled = new File(dir, "filled.txt");
            FileOutputStream out = new FileOutputStream(filled);
            try {
                out.write(new byte[]{1, 2, 3, 4});
            } finally {
                out.close();
            }
            assertFalse(FileUtil.isEmpty(filled));
            assertEquals(4L, FileUtil.size(filled));

            File child = new File(dir, "child.txt");
            assertTrue(child.createNewFile());
            assertFalse(FileUtil.isEmpty(dir));
            assertEquals(0L, FileUtil.size(dir));
            assertTrue(FileUtil.isDirectory(dir));
            assertFalse(FileUtil.isDirectory(filled));
            assertFalse(FileUtil.isDirectory(missing));
            assertFalse(FileUtil.isDirectory(null));
            assertTrue(FileUtil.isFile(filled));
            assertTrue(FileUtil.isFile(empty));
            assertFalse(FileUtil.isFile(dir));
            assertFalse(FileUtil.isFile(missing));
            assertFalse(FileUtil.isFile(null));
            assertTrue(FileUtil.exists(filled));
            assertTrue(FileUtil.exists(dir));
            assertFalse(FileUtil.exists(missing));
            assertFalse(FileUtil.exists(null));
        } finally {
            deleteTree(dir);
        }
    }

    @Test
    public void deleteQuietly_deletesFileOrEmptyDirectoryWithoutRecursing() throws Exception {
        assertFalse(FileUtil.deleteQuietly(null));

        File dir = newTempDir();
        try {
            File missing = new File(dir, "missing.txt");
            assertFalse(FileUtil.deleteQuietly(missing));

            File file = new File(dir, "gone.txt");
            assertTrue(file.createNewFile());
            assertTrue(FileUtil.deleteQuietly(file));
            assertFalse(file.exists());

            File emptyDir = new File(dir, "empty");
            assertTrue(emptyDir.mkdir());
            assertTrue(FileUtil.deleteQuietly(emptyDir));
            assertFalse(emptyDir.exists());

            File parent = new File(dir, "parent");
            assertTrue(parent.mkdir());
            File child = new File(parent, "keep.txt");
            assertTrue(child.createNewFile());
            assertFalse(FileUtil.deleteQuietly(parent));
            assertTrue(parent.exists());
            assertTrue(child.exists());
        } finally {
            deleteTree(dir);
        }
    }

    private static File newTempDir() {
        File dir = new File(System.getProperty("java.io.tmpdir"), "fileutil-" + System.nanoTime());
        assertTrue(dir.mkdir());
        return dir;
    }

    private static void deleteTree(File file) {
        File[] children = file.listFiles();
        if (children != null) {
            for (int i = 0; i < children.length; i++) {
                deleteTree(children[i]);
            }
        }
        file.delete();
    }
}
