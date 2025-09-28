package me.zort.acs.plane.api.domain.storage;

import java.util.regex.Pattern;

public final class BlobPath {
    private final String[] segments;

    public BlobPath(String[] segments) throws IllegalArgumentException {
        validateSegments(segments);

        this.segments = segments;
    }

    /**
     * Validates the segments of the path.
     *
     * @param segments the segments to validate
     * @throws IllegalArgumentException if the segments array is empty or contains invalid segments
     */
    private static void validateSegments(String[] segments) throws IllegalArgumentException {
        // TODO
    }

    /**
     * Compiles a string path into a BlobPath object.
     *
     * @param path the string path to compile
     * @return a BlobPath object representing the compiled path
     * @throws IllegalArgumentException if the path is invalid
     */
    public static BlobPath compile(String path) throws IllegalArgumentException {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        String[] segments = path.split("/");

        return new BlobPath(segments);
    }

    /**
     * Converts the BlobPath to a string representation.
     *
     * @return the string representation of the BlobPath
     */
    public String toPathString() {
        return "/" + String.join("/", segments);
    }

    /**
     * Returns a regex Pattern that matches this path and any subfiles.
     *
     * @return a Pattern object that matches this path and its subfiles
     */
    public Pattern getMatchSubfilesPattern() {
        return Pattern.compile("^/" + String.join("/", segments) + "(/.*)?$");
    }
}
