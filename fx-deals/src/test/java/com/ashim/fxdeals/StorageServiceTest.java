package com.ashim.fxdeals;

import com.ashim.fxdeals.service.StorageService;
import com.ashim.fxdeals.util.StorageProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class StorageServiceTest {

    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageProperties properties;

    private static final String defaultResource = "sample_resource.csv";

    @Before
    public void setUp() throws IOException {
        InputStream is = StorageServiceTest.class.getClassLoader().getResourceAsStream(defaultResource);
        Path path = Paths.get(defaultResource);
        Path rootLocation = Paths.get(this.properties.getLocation());

        Files.copy(is, rootLocation.resolve(path), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void shouldUploadFile() throws Exception {
        InputStream uploadStream = StorageServiceTest.class.getClassLoader().getResourceAsStream("sample.csv");
        MockMultipartFile file = new MockMultipartFile("sample.csv",
                "sample.csv", "text/csv", FileCopyUtils.copyToByteArray(uploadStream));

        assert uploadStream != null;
        this.storageService.storeFile(file);

        Stream<Path> pathStream = this.storageService.loadAll();
        Optional<Path> csvPath = pathStream
                .filter(path -> "sample.csv".equalsIgnoreCase(path.getFileName().toString()))
                .findFirst();

        assertTrue(csvPath.isPresent());
    }

    @Test
    public void shouldReturnResource() {
        Resource resource = this.storageService.loadAsResource(defaultResource);
        assertTrue(resource.exists());
        assertEquals(defaultResource, resource.getFilename());
    }
}
