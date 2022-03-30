package io.lycoris;

import com.twitter.scrooge.CompilerDefaults;
import com.twitter.scrooge.Main$;
import com.twitter.scrooge.ScroogeConfig;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import scala.Option;
import scala.collection.JavaConverters;


import com.twitter.scrooge.Compiler;
import com.twitter.scrooge.Main;
import scala.collection.immutable.List$;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Seq$;


public class ScroogeCompileTask extends DefaultTask {
    private File _dest = new File("/src/gen/java/");
    private Iterable<File> _files = Collections.singletonList(new File("/src/main/thrift/"));
    private List<String> _opts = Collections.singletonList("-v");
    private String _lang = "java";

    @OutputDirectory
    public File getDest() {
        return _dest;
    }

    public void setDest(File destinationDirectory) {
        _dest = destinationDirectory;
    }


    @InputFiles
    public Iterable<File> getThriftFiles() {
        return _files;
    }

    public void setThriftFiles(Iterable<File> files) {
        _files = files;
    }

    @Input
    @Optional
    public List<String> getOpts() {
        return _opts;
    }

    public void setOpts(List<String> opts) {
        _opts = opts;
    }

    @TaskAction
    public void compile() {
        String destination = getDest().getAbsolutePath();
        List<String> thriftFiles = new ArrayList<>();

        for (File item : _files) {
            thriftFiles.add(item.getAbsolutePath());
        }

        thriftFiles.forEach(item -> System.out.println(item));

        Option<String> opt = Option.empty();
        Map<String, String> m = new HashMap<>();
        ScroogeConfig config = new ScroogeConfig(
                destination,
                List$.MODULE$.empty(),
                JavaConverters.asScala(thriftFiles).toList(),
                JavaConverters.asScala(_opts).toSet(),
                Map$.MODULE$.empty(),
                false,
                true,
                false,
                false,
                Seq$.MODULE$.empty(),
                opt,
                false,
                _lang,
                CompilerDefaults.defaultNamespace(),
                false,
                false,
                true);
        Compiler compiler = new Compiler(config);

        compiler.run();
    }
}
