/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testzbox.vista;

import com.mx.intellego.zurich.ecm.intercambio.VerificarChecklist;
import com.mx.intellego.zurich.ecm.vo.CheckListVO;
import com.mx.intellego.zurich.ecm.vo.DocumentoVO;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edrd
 */
public class TabChecklist extends VerticalLayout {

    FormLayout form = new FormLayout();
    HorizontalLayout footer = new HorizontalLayout();
    Panel panel1 = new Panel();
    Panel panel2 = new Panel();
    Table tblSubtipos = new Table();

    ComboBox cmbChecklist;
    TextField txtNumSiniestro;

    Button btnVerificar;

    public TabChecklist() {
        initElements();
    }

    private void initElements() {

        txtNumSiniestro = new TextField("Número Siniestro");
        txtNumSiniestro.setWidth(30.0f, Sizeable.Unit.PERCENTAGE);

        cmbChecklist = new ComboBox("Checklist");
        cmbChecklist.setWidth(30.0f, Sizeable.Unit.PERCENTAGE);
        cmbChecklist.setNullSelectionAllowed(false);
        cmbChecklist.setTextInputAllowed(false);
        cmbChecklist.setInputPrompt("Seleccione");
        cmbChecklist.addItem("CHKLST_AJT_ASG_001");
        cmbChecklist.addItem("CHKLST_AJT_TRC_001");
        cmbChecklist.addItem("CHKLST_PAG_PT_006");
        cmbChecklist.addItem("CHKLST_PAG_PD_001");
        
        form.addComponents(txtNumSiniestro,
                           cmbChecklist);
        

        btnVerificar = new Button("Verificar");
        btnVerificar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnVerificar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                CheckListVO checkListVO = new CheckListVO();

                checkListVO.setIdCheckList(cmbChecklist.getValue().toString());

                Map<String, String> parametros = new HashMap<>();
                parametros.put("numeroSiniestro", txtNumSiniestro.getValue());

                checkListVO.setParametros(parametros);

                verificarChecklist(checkListVO);

            }

        });

        footer.setSpacing(true);
        footer.setWidth(38.0f, Unit.PERCENTAGE);
        footer.addComponents(btnVerificar);
        footer.setComponentAlignment(btnVerificar, Alignment.BOTTOM_RIGHT);

        panel1.setVisible(false);
        
        
        

        setCaption("Checklist");
        setSpacing(true);
        setMargin(true);
        addComponents(form,
                footer,
                panel1);

    }

    private void verificarChecklist(CheckListVO checkListVO) {

        VerificarChecklist verChecklist = new VerificarChecklist();

        List<DocumentoVO> documentoVoLt = verChecklist.verificarChecklist(checkListVO, checkListVO);

        panel1.setContent(
                new Label(String.format("Total: <b>%s</b> </br>"
                        + "Total Existentes: <b>%s</b> </br>"
                        + "Obligatorios: <b>%s</b> </br>"
                        + "Opcionales: <b>%s</b> </br>"
                        + "Mensaje: <b>%s</b> </br>",
                        checkListVO.getDocumentosTotal(),
                        checkListVO.getDocumentosExistentes(),
                        checkListVO.getDocumentosObligatorios(),
                        checkListVO.getDocumentosOpcionales(),
                        checkListVO.getMensaje()), ContentMode.HTML)
        );

//        String lbl = "";
//        int contador = 1;
//        for (DocumentoVO doc : documentoVoLt) {
//            lbl += String.format(
//                    "<b>No:</b> %s, <b>Id:</b> %s, <b>Nombre:</b> %s, <b>Tipo Documental:</b> %s, <b>Cargado:</b> %s, <b>Area:</b> %s, <b>Obligatorio:</b> %s </br>",
//                    contador++, doc.getIdDocumento(), doc.getNombreDocumento(), doc.getTipoDocumental(), doc.getCargado(), doc.getAreaResponsable(), doc.getObligatorioCheckList());
//
//        }
//
//        panel2.setContent(
//                new Label(lbl, ContentMode.HTML)
//        );

        panel1.setVisible(true);
        //panel2.setVisible(true);

        tblSubtipos.setWidth("100%");
        tblSubtipos.setSelectable(false);
        tblSubtipos.setImmediate(true);
        //tblSubtipos.setPageLength();
        tblSubtipos.refreshRowCache();
        tblSubtipos.addContainerProperty("No.", Integer.class, "");
        tblSubtipos.addContainerProperty("Id Documento", String.class, "");
        tblSubtipos.addContainerProperty("Nombre Documento", String.class, "");
        tblSubtipos.addContainerProperty("Tipo Documental", String.class, "");
        tblSubtipos.addContainerProperty("Cargado", Label.class, "");
        tblSubtipos.addContainerProperty("Area", String.class, "");
        tblSubtipos.addContainerProperty("Obligatorio", String.class, "");
        tblSubtipos.setContainerDataSource(crearContenedor(checkListVO));
        
        addComponent(tblSubtipos);
    }
    
    private IndexedContainer crearContenedor(CheckListVO checkListVO) {
        IndexedContainer idxCont = new IndexedContainer();
        
        idxCont.addContainerProperty("No.", Integer.class, "");
        idxCont.addContainerProperty("Id Documento", String.class, "");
        idxCont.addContainerProperty("Nombre Documento", String.class, "");
        idxCont.addContainerProperty("Tipo Documental", String.class, "");
        idxCont.addContainerProperty("Cargado", Label.class, "");
        idxCont.addContainerProperty("Area", String.class, "");
        idxCont.addContainerProperty("Obligatorio", String.class, "");
        
        VerificarChecklist verChecklist = new VerificarChecklist();

        List<DocumentoVO> documentoVoLt = verChecklist.verificarChecklist(checkListVO, checkListVO);
        int contador = 1;
        for (DocumentoVO doc : documentoVoLt) {
            Item item = idxCont.getItem(idxCont.addItem());
            item.getItemProperty("No.").setValue(contador++);
            item.getItemProperty("Id Documento").setValue(doc.getIdDocumento() != null ? doc.getIdDocumento() : "N.E.");
            item.getItemProperty("Nombre Documento").setValue(doc.getNombreDocumento() != null ? doc.getNombreDocumento() : "N.E.");
            item.getItemProperty("Tipo Documental").setValue(doc.getTipoDocumental());
            item.getItemProperty("Cargado").setValue(doc.getCargado() == true ? new Label(FontAwesome.CHECK.getHtml(), ContentMode.HTML) : new Label(FontAwesome.CLOSE.getHtml(), ContentMode.HTML) );
            item.getItemProperty("Area").setValue(doc.getAreaResponsable());
            item.getItemProperty("Obligatorio").setValue(doc.getObligatorioCheckList() == true ? "SI" : "NO" );
        }
        
        
        return idxCont;
    }

}
