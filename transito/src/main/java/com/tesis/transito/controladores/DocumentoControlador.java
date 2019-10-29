package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.ArchivoParam;
import com.tesis.dominio.modelos.Documento;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;

@RestController
@RequestMapping(path = "/documentos")
public class DocumentoControlador {

    private final CasoDeUso<ArchivoParam, Documento> casoDeUsoGuardarDocumentos;
    private final CasoDeUso<String, Resource> casoDeUsoBuscarDocumentos;

    public DocumentoControlador(CasoDeUso<ArchivoParam, Documento> casoDeUsoGuardarDocumento,
                                CasoDeUso<String, Resource> casoDeUsoBuscarDocumentos) {
        this.casoDeUsoGuardarDocumentos = casoDeUsoGuardarDocumento;
        this.casoDeUsoBuscarDocumentos = casoDeUsoBuscarDocumentos;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<Documento> guardarArchivos(@RequestBody Flux<Part> archivos) throws IOException {
        return archivos
                .filter(archivo -> archivo instanceof FilePart)
                .ofType(FilePart.class)
                .flatMap(archivo -> casoDeUsoGuardarDocumentos.ejecutar(new ArchivoParam(archivo.filename(), archivo.content())));
    }

    @GetMapping("/{nombre}")
    public Flux<Resource> buscarDocumentos(@PathVariable String nombre) {
        return casoDeUsoBuscarDocumentos.
                ejecutar(nombre);
    }

    /*@PostMapping("test")
    public Flux<String> test(@RequestBody Flux<Part> parts) {
        return parts
                .filter(part -> part instanceof FilePart) // only retain file parts
                .ofType(FilePart.class) // convert the flux to FilePart
                .flatMap(this::saveFile); // save each file and flatmap it to a flux of results
    }*/

    /**
     * tske a {@link FilePart}, transfer it to disk using {@link AsynchronousFileChannel}s and return a {@link Mono} representing the result
     *
     * @param filePart - the request part containing the file to be saved
     * @return a {@link Mono} representing the result of the operation
     */
    /*private Mono<String> saveFile(FilePart filePart) {

        // if a file with the same name already exists in a repository, delete and recreate it
        final String filename = filePart.filename();
        File file = new File(filename);
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            return Mono.error(e); // if creating a new file fails return an error
        }

        try {
            // create an async file channel to store the file on disk
            final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(file.toPath(), StandardOpenOption.WRITE);

            final CloseCondition closeCondition = new CloseCondition();

            // pointer to the end of file offset
            AtomicInteger fileWriteOffset = new AtomicInteger(0);
            // error signal
            AtomicBoolean errorFlag = new AtomicBoolean(false);

            // FilePart.content produces a flux of data buffers, each need to be written to the file
            return filePart.content().doOnEach(dataBufferSignal -> {
                if (dataBufferSignal.hasValue() && !errorFlag.get()) {
                    // read data from the incoming data buffer into a file array
                    DataBuffer dataBuffer = dataBufferSignal.get();
                    int count = dataBuffer.readableByteCount();
                    byte[] bytes = new byte[count];
                    dataBuffer.read(bytes);

                    // create a file channel compatible byte buffer
                    final ByteBuffer byteBuffer = ByteBuffer.allocate(count);
                    byteBuffer.put(bytes);
                    byteBuffer.flip();

                    // get the current write offset and increment by the buffer size
                    final int filePartOffset = fileWriteOffset.getAndAdd(count);
                    // write the buffer to disk
                    closeCondition.onTaskSubmitted();
                    fileChannel.write(byteBuffer, filePartOffset, null, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            // file part successfuly written to disk, clean up
                            byteBuffer.clear();

                            if (closeCondition.onTaskCompleted())
                                try {
                                    fileChannel.close();
                                } catch (IOException ignored) {
                                    ignored.printStackTrace();
                                }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            // there as an error while writing to disk, set an error flag
                            errorFlag.set(true);
                        }
                    });
                }
            }).doOnComplete(() -> {
                // all done, close the file channel

                if (closeCondition.canCloseOnComplete())
                    try {

                        fileChannel.close();
                    } catch (IOException ignored) {
                    }

            }).doOnError(t -> {
                // ooops there was an error

                try {
                    fileChannel.close();
                } catch (IOException ignored) {
                }
                // take last, map to a status string
            }).last().map(dataBuffer -> filePart.filename() + " " + (errorFlag.get() ? "error" : "uploaded"));
        } catch (IOException e) {
            // unable to open the file channel, return an error

            return Mono.error(e);
        }
    }*/
}
