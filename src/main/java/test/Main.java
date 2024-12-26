package test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class Main {

  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.err.println("2 parameters expected:");
      System.err.println("split-pdf {pdfs_source_file} {clear_annotations}");
      System.exit(1);
    }

    Path fileToSplit = Path.of(args[0]);
    boolean clearAnnotations = Boolean.parseBoolean(args[1]);

    try (var document = Loader.loadPDF(fileToSplit.toFile())) {
      if (clearAnnotations) {
        clearAnnotations(document);
      }
      
      splitDocument(document);
    }
  }

  private static void splitDocument(PDDocument document) throws IOException {
    AtomicInteger counter = new AtomicInteger(0);
    Splitter splitter = new Splitter();
    splitter.setSplitAtPage(20);
    splitter.split(document)
      .forEach(d -> {
        try {
          d.save("/tmp/split-" + counter.getAndIncrement() + ".pdf");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
  }

  private static void clearAnnotations(PDDocument document) throws IOException {
    for (int i = 0; i < document.getNumberOfPages(); i++) {
      document.getPage(i).getAnnotations().clear();
    }
  }
}