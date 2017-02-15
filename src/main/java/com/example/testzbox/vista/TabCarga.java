/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testzbox.vista;

import com.mx.intellego.zurich.ecm.intercambio.CargarDocumento;
import com.mx.intellego.zurich.ecm.util.Respuesta;
import com.mx.intellego.zurich.ecm.vo.DocumentoVO;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;

/**
 *
 * @author Edrd
 */
public class TabCarga extends HorizontalLayout {

    ProgressBar progressBar = new ProgressBar(0.0f);

    FormLayout form = new FormLayout();
    HorizontalLayout footer = new HorizontalLayout();
    VerticalLayout layout = new VerticalLayout();

    Button btnEnviar;
    Plupload uploader;

    ComboBox cmbTipoDoc;
    ComboBox cmbArea;
    ComboBox cmbOperacion;
    TextField txtNombreDoc;
    Label lblPathArchivo;
    Label lblExtensionArchivo;

    //TIPO SINIESTRO
    TextField txtNumSiniestro;
    TextField txtNumPoliza;
    TextField txtNombAsegurado;
    DateField dateFechaDocumento;
    TextField txtIdUsuarioCmp;

    //TIPO ACTA
    DateField dateFechaExpedicion;
    TextField txtFolioActa;
    ComboBox cmbTipoEvento;

    Panel panel = new Panel();
    
    public TabCarga() {
        
        setSizeFull();
        
        iniciarElementos();
        
        panel.setVisible(true);
        
        
    }

    private void iniciarElementos() {
        
        //layout.setSpacing(true);
        layout.setWidth(200.0f, Unit.PERCENTAGE);
        //layout.setSizeFull();
        
        cmbArea = new ComboBox("Area");
        cmbArea.setWidth(30.0f, Unit.PERCENTAGE);
        cmbArea.setNullSelectionAllowed(false);
        cmbArea.setTextInputAllowed(false);
        cmbArea.setInputPrompt("Seleccione");
        cmbArea.addItem("adjustment");
//        cmbArea.addItem("theft");
//        cmbArea.addItem("siu");
//        cmbArea.addItem("glasses");
//        cmbArea.addItem("payments");
//        cmbArea.addItem("valuation");
//                
        cmbOperacion = new ComboBox("Operación");
        cmbOperacion.setWidth(30.0f, Unit.PERCENTAGE);
        cmbOperacion.setNullSelectionAllowed(false);
        cmbOperacion.setTextInputAllowed(false);
        cmbOperacion.setInputPrompt("Seleccione");
        cmbOperacion.addItem("Asegurado");
        cmbOperacion.addItem("Tercero");
        

        txtNombreDoc = new TextField("Nombre Documento");
        txtNombreDoc.setWidth(30.0f, Unit.PERCENTAGE);

        //SINIESTRO
        txtNumSiniestro = new TextField("Número Siniestro");
        txtNumSiniestro.setWidth(30.0f, Unit.PERCENTAGE);
        txtNumPoliza = new TextField("Número de Póliza");
        txtNumPoliza.setWidth(30.0f, Unit.PERCENTAGE);
        txtNombAsegurado = new TextField("Nombre Asegurado");
        txtNombAsegurado.setWidth(30.0f, Unit.PERCENTAGE);
        dateFechaDocumento = new DateField("Fecha Creación");
        dateFechaDocumento.setWidth(30.0f, Unit.PERCENTAGE);
        txtIdUsuarioCmp = new TextField("Id Usuario");
        txtIdUsuarioCmp.setWidth(30.0f, Unit.PERCENTAGE);

        //TIPO DOCUMENTAL
        cmbTipoDoc = new ComboBox("Tipo Documental");
        cmbTipoDoc.setWidth(30.0f, Unit.PERCENTAGE);
        cmbTipoDoc.setNullSelectionAllowed(false);
        cmbTipoDoc.setTextInputAllowed(false);
        cmbTipoDoc.setInputPrompt("Seleccione");
        cmbTipoDoc.addItem("averiguacionPrevia");
        cmbTipoDoc.addItem("declaracionUniversalAccidente");
        cmbTipoDoc.addItem("declaracionUniversalAccidenteSIPAC");
        cmbTipoDoc.addItem("demanda");
        cmbTipoDoc.addItem("poderNotarial");
        cmbTipoDoc.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                dateFechaExpedicion.setVisible(true);
                txtFolioActa.setVisible(true);
                cmbTipoEvento.setVisible(true);
            }
        });

        dateFechaExpedicion = new DateField("Fecha Expedición");
        dateFechaExpedicion.setWidth(30.0f, Unit.PERCENTAGE);
        dateFechaExpedicion.setVisible(false);
        txtFolioActa = new TextField("Folio Acta");
        txtFolioActa.setWidth(30.0f, Unit.PERCENTAGE);
        txtFolioActa.setVisible(false);
        cmbTipoEvento = new ComboBox("Tipo Evento");
        cmbTipoEvento.setWidth(30.0f, Unit.PERCENTAGE);
        cmbTipoEvento.setVisible(false);
        cmbTipoEvento.setNullSelectionAllowed(false);
        cmbTipoEvento.setTextInputAllowed(false);
        cmbTipoEvento.setInputPrompt("Seleccione");
        cmbTipoEvento.addItem("Averiguación Previa");
        cmbTipoEvento.addItem("DUA");
        cmbTipoEvento.addItem("DUA SIPAC");
        cmbTipoEvento.addItem("DUA Cristaleras");
        cmbTipoEvento.addItem("Demanda");
        cmbTipoEvento.addItem("Poder Notarial");
        
        lblPathArchivo = new Label();
        lblPathArchivo.setVisible(false);
        lblExtensionArchivo = new Label();
        lblExtensionArchivo.setVisible(false);

        //  form.setSpacing(true);
        form.addComponents(
                cmbArea,
                cmbOperacion,
                txtNombreDoc,
                txtNumSiniestro,
                txtNumPoliza,
                txtNombAsegurado,
                dateFechaDocumento,
                txtIdUsuarioCmp,
                cmbTipoDoc,
                dateFechaExpedicion,
                txtFolioActa,
                cmbTipoEvento);

        uploader = uploadContents();
        btnEnviar = new Button("Enviar");
        btnEnviar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnEnviar.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                //Path pathFile = Paths.get("C:\\Documento Prueba.pdf");
                Path pathFile = Paths.get(lblPathArchivo.getCaption());

                DocumentoVO documentoVO = new DocumentoVO();

                documentoVO.setNombreDocumento(txtNombreDoc.getValue() + "." + lblExtensionArchivo.getCaption());
                documentoVO.setSubTipoDocumental(cmbTipoDoc.getValue().toString());
                documentoVO.setInputStream(pathFile.toFile());

                Map<String, String> metadatos = new HashMap<>();
                metadatos.put("folioActa", txtFolioActa.getValue());
                metadatos.put("tipoEvento", cmbTipoEvento.getValue().toString());
                metadatos.put("fechaExpedicion", convertDate(dateFechaExpedicion));
                metadatos.put("numeroSiniestro", txtNumSiniestro.getValue());
                metadatos.put("numeroPoliza", txtNumPoliza.getValue());
                metadatos.put("nombreAsegurado", txtNombAsegurado.getValue());
                metadatos.put("fechaCreacionDocumento", convertDate(dateFechaDocumento));
                metadatos.put("idUsuarioCmp", txtIdUsuarioCmp.getValue());
                metadatos.put("area", cmbArea.getValue().toString());
                metadatos.put("operacion", cmbOperacion.getValue().toString());
                
                documentoVO.setMetadatos(metadatos);

                cargarDocumento(documentoVO);

            }

        });

        footer.setSpacing(true);
        footer.setWidth(38.0f, Unit.PERCENTAGE);
        footer.addComponents(uploader,
                             btnEnviar);
        footer.setComponentAlignment(btnEnviar, Alignment.BOTTOM_RIGHT);

        layout.addComponents(form,
                            lblPathArchivo,
                            footer);
        
        setCaption("Carga");
        setMargin(true);
        //setSpacing(true);
        addComponents(layout, panel);

    }

    private Plupload uploadContents() {

        Plupload uploader = new Plupload("Cargar", FontAwesome.UPLOAD);
        uploader.setPreventDuplicates(true);
        uploader.setUploadPath("D:\\vaadin\\TestZBox\\upload");
        uploader.setMaxFileSize("5mb");

//show notification after file is uploaded
        uploader.addFileUploadedListener(new Plupload.FileUploadedListener() {
            @Override
            public void onFileUploaded(PluploadFile file) {

                /**
                 * CAMBIAR EL NOMBRE DEL ARCHIVO QUE SE SUBE, YA QUE NO RESPETA
                 * EL NOMBRE DEL ARCHIVO ORIGINAL
                 */
                File uploadedFile = (File) file.getUploadedFile();
                System.out.println("uploadedFile = " + uploadedFile);
                // NOMBRE CORRECTO
                String realName = file.getName();
                // NOMBRE INCORRECTO
                String falseName = uploadedFile.getName();
                // PATH DEL ARCHIVO
                String pathFile = uploadedFile.getAbsolutePath();
                pathFile = pathFile.substring(0, pathFile.lastIndexOf("\\"));
                System.out.println("pathFile = " + pathFile);
                // SE CREAN LOS OBJETIPOS DE TIPO FILE DE CADA UNO
                File fileFalse = new File(pathFile + "\\" + falseName);
                File fileReal = new File(pathFile + "\\" + realName);
                // SE REALIZA EL CAMBIO DE NOMBRE DEL ARCHIVO
                boolean cambio = fileFalse.renameTo(fileReal);
                
                lblPathArchivo.setCaption(fileReal.toString());

            }
        });

//update upload progress
        uploader.addUploadProgressListener(new Plupload.UploadProgressListener() {
            @Override
            public void onUploadProgress(PluploadFile file) {

                progressBar.setWidth("128px");
                //progressBar.setStyleName(ValoTheme.PROGRESSBAR_POINT);
                progressBar.setVisible(true);

                progressBar.setValue(new Long(file.getPercent()).floatValue() / 100);
                progressBar.setDescription(file.getPercent() + "%");

                System.out.println("I'm uploading " + file.getName()
                        + " and I'm at " + file.getPercent() + "%");
                
                lblExtensionArchivo.setCaption(file.getName().substring(file.getName().lastIndexOf('.') + 1));
            }
        });

//autostart the uploader after addind files
        uploader.addFilesAddedListener(new Plupload.FilesAddedListener() {
            @Override
            public void onFilesAdded(PluploadFile[] files) {
                progressBar.setValue(0f);
                progressBar.setVisible(true);
                uploader.start();
            }
        });

//notify, when the upload process is completed
        uploader.addUploadCompleteListener(new Plupload.UploadCompleteListener() {
            @Override
            public void onUploadComplete() {
                System.out.println("upload is completed!");
            }
        });

//handle errors
        uploader.addErrorListener(new Plupload.ErrorListener() {
            @Override
            public void onError(PluploadError error) {
                Notification.show("There was an error: "
                        + error.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        });

        return uploader;
    }

    private void cargarDocumento(DocumentoVO documentoVO) {
        
        CargarDocumento cargarDoc = new CargarDocumento();
        
        Boolean resultado = cargarDoc.cargarDocumento(documentoVO);
        System.out.println("resultado = " + resultado);

        Respuesta respuesta = documentoVO.getRespuesta();
        
        panel.setContent(
                new Label(String.format("Resp. Operacion: <b>%s</b> </br>"
                        + "Cod. Operacion: <b>%s</b> </br>"
                        + "Detalle: <b>%s</b> </br>"
                        + "Id documento: <b>%s</b> </br>",
                        respuesta.getRespuestaOperacion(),
                        respuesta.getCodigoOperacion(),
                        encodeCharSet(respuesta.getDetalleOperacion()),
                        respuesta.getIdDocumento()), ContentMode.HTML)
        );
        
        //panel.setVisible(true);
        
    }

    private String convertDate(DateField fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //FORMATO TIME-STAMP QUE RECIBE BOX PARA LA FECHA
        String date = sdf.format(new Date(fecha.getValue().toString()));
        System.out.println("date = " + date);
        
        return date;
    }
    
    private String encodeCharSet(String palabra) {
        //System.out.println("file.encoding=" + System.getProperty("file.encoding"));   //OTRA OPCION
        Charset defaultCharset = Charset.defaultCharset();
        String stringEncode = defaultCharset.contains(StandardCharsets.UTF_8) ? palabra
                        : new String(palabra.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        return stringEncode;
    }
}
