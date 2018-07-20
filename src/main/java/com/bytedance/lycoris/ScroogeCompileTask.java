package com.bytedance.lycoris;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import scala.collection.JavaConverters;


import com.twitter.scrooge.Compiler;
import com.twitter.scrooge.Main;


public class ScroogeCompileTask extends DefaultTask {
    private File _dest = new File("/src/main/thrift/");
    private Iterable<File> _files = Arrays.asList(new File("./"));
    private List<String> _opts = Arrays.asList("");
    private String _lang = "java";

    @OutputDirectory
    private File getDest = _dest;

    public void setDest(File destinationDirectory){
        _dest = destinationDirectory;
    }


    @InputFiles
    private Iterable<File> getThriftFiles = _files;

    public void setThriftFiles(Iterable<File> files){
        _files = files;
    }

    @Input
    @Optional
    private List<String> getOpts = _opts;

    public void setOpts(List<String> opts){
        _opts = opts;
    }

    @TaskAction
    public void compile() {
        String destination = getDest.getAbsolutePath();
        List<String> thriftFiles = new ArrayList<>();

        for(File item : _files){
            thriftFiles.add(item.getAbsolutePath());
        }

        thriftFiles.forEach(item -> System.out.println(item));
        Compiler compiler = new Compiler();
        compiler.destFolder_$eq(destination);
        compiler.language_$eq(_lang);


        List<String> args = new ArrayList<>();
        args.addAll(_opts);
        args.addAll(thriftFiles);

        Main.parseOptions(compiler,JavaConverters.asScalaIteratorConverter(args.iterator()).asScala().toSeq());
        compiler.run();
    }
}
