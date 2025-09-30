package me.zort.acs.plane.api.domain.storage;

import org.jetbrains.annotations.NotNull;

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
        if (segments.length == 0) {
            throw new IllegalArgumentException("Path cannot be empty");
        }

        for (String segment : segments) {
            if (segment == null || segment.isBlank()) {
                throw new IllegalArgumentException("Segment of path cannot be null or blank");
            }
        }
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

    /**
     * Append the provided blob path at the end of this path.
     *
     * @param append The path to append
     * @return The result path
     */
    @NotNull
    public BlobPath append(BlobPath append) {
        String[] newSegments = new String[segments.length + append.segments.length];
        System.arraycopy(segments, 0, newSegments, 0, segments.length);
        System.arraycopy(append.segments, 0, newSegments, segments.length, append.segments.length);

        return new BlobPath(newSegments);
    }
}
