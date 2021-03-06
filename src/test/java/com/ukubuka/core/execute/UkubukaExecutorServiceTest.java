package com.ukubuka.core.execute;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ukubuka.core.exception.ParserException;
import com.ukubuka.core.exception.TransformException;
import com.ukubuka.core.exception.WriterException;
import com.ukubuka.core.model.FileContents;
import com.ukubuka.core.model.UkubukaSchema;
import com.ukubuka.core.operations.extract.UkubukaExtractor;
import com.ukubuka.core.operations.load.UkubukaLoader;
import com.ukubuka.core.operations.transform.UkubukaTransformer;
import com.ukubuka.core.schema.UkubukaSchemaReader;

/**
 * Ukubuka Executor Services Test
 * 
 * @author agrawroh
 * @version v1.0
 */
public class UkubukaExecutorServiceTest {

    /**************************** Dependency Mocks ***************************/
    @Mock
    private UkubukaSchemaReader ukubukaSchemaReader;

    @Mock
    private UkubukaExtractor ukubukaExtractor;

    @Mock
    private UkubukaTransformer ukubukaTransformer;

    @Mock
    private UkubukaLoader ukubukaLoader;

    @Mock
    private UkubukaSchema ukubukaSchema;

    @InjectMocks
    private UkubukaExecutorService ukubukaExecutorService;

    /**************************** Initialize Mocks ***************************/
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /******************************** Test(s) ********************************/
    @Test
    public void test_execute_success()
            throws ParserException, TransformException, WriterException {
        Mockito.when(ukubukaSchemaReader.readSchema(Mockito.anyString()))
                .thenReturn(new UkubukaSchema());

        Mockito.when(ukubukaExtractor.performOperations(
                Mockito.anyListOf(UkubukaSchema.Extract.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaTransformer.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Transform.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaLoader.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Load.class)))
                .thenReturn(new HashMap<String, FileContents>());

        ukubukaExecutorService.execute("foobar");

        Mockito.when(ukubukaSchemaReader.readSchema(Mockito.anyString()))
                .thenReturn(new UkubukaSchema());

        Mockito.verify(ukubukaExtractor, Mockito.times(1)).performOperations(
                Mockito.anyListOf(UkubukaSchema.Extract.class));

        Mockito.verify(ukubukaTransformer, Mockito.times(1)).performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Transform.class));

        Mockito.verify(ukubukaLoader, Mockito.times(1)).performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Load.class));
    }

    @Test(expected = ParserException.class)
    public void test_execute_readSchema_failure()
            throws ParserException, TransformException, WriterException {
        Mockito.when(ukubukaSchemaReader.readSchema(Mockito.anyString()))
                .thenThrow(new ParserException("foo"));

        Mockito.when(ukubukaExtractor.performOperations(
                Mockito.anyListOf(UkubukaSchema.Extract.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaTransformer.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Transform.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaLoader.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Load.class)))
                .thenReturn(new HashMap<String, FileContents>());

        ukubukaExecutorService.execute("foobar");
    }

    @Test(expected = ParserException.class)
    public void test_execute_extract_failure()
            throws ParserException, TransformException, WriterException {
        Mockito.when(ukubukaSchemaReader.readSchema(Mockito.anyString()))
                .thenReturn(new UkubukaSchema());

        Mockito.when(ukubukaExtractor.performOperations(
                Mockito.anyListOf(UkubukaSchema.Extract.class)))
                .thenThrow(new ParserException("foo"));

        Mockito.when(ukubukaTransformer.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Transform.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaLoader.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Load.class)))
                .thenReturn(new HashMap<String, FileContents>());

        ukubukaExecutorService.execute("foobar");
    }

    @Test(expected = TransformException.class)
    public void test_execute_transform_failure()
            throws ParserException, TransformException, WriterException {
        Mockito.when(ukubukaSchemaReader.readSchema(Mockito.anyString()))
                .thenReturn(new UkubukaSchema());

        Mockito.when(ukubukaExtractor.performOperations(
                Mockito.anyListOf(UkubukaSchema.Extract.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaTransformer.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Transform.class)))
                .thenThrow(new TransformException("foo"));

        Mockito.when(ukubukaLoader.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Load.class)))
                .thenReturn(new HashMap<String, FileContents>());

        ukubukaExecutorService.execute("foobar");
    }

    @Test(expected = WriterException.class)
    public void test_execute_load_failure()
            throws ParserException, TransformException, WriterException {
        Mockito.when(ukubukaSchemaReader.readSchema(Mockito.anyString()))
                .thenReturn(new UkubukaSchema());

        Mockito.when(ukubukaExtractor.performOperations(
                Mockito.anyListOf(UkubukaSchema.Extract.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaTransformer.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Transform.class)))
                .thenReturn(new HashMap<String, FileContents>());

        Mockito.when(ukubukaLoader.performOperations(
                Mockito.anyMapOf(String.class, FileContents.class),
                Mockito.anyListOf(UkubukaSchema.Load.class)))
                .thenThrow(new WriterException("foo"));

        ukubukaExecutorService.execute("foobar");
    }
}
