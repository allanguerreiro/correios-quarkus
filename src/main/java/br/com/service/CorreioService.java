package br.com.service;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.correios.webservice.resource.Sroxml;
import lombok.extern.slf4j.Slf4j;
import br.com.correios.webservice.resource.Service;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.namespace.QName;
import java.net.URL;

@Slf4j
@ApplicationScoped
public class CorreioService {

    public EnderecoERP consultarCep(String cep) throws Exception {
        AtendeCliente atendeCliente = getService("https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl",
                "http://cliente.bean.master.sigep.bsb.correios.com.br/", "AtendeClienteService")
                .getPort(AtendeCliente.class);
        return atendeCliente.consultaCEP(cep);
    }

    public Sroxml ratrearPacote(String codigo) throws Exception {
        Service serviceRastro = getService("http://webservice.correios.com.br/service/rastro/Rastro.wsdl",
                "http://resource.webservice.correios.com.br/", "rastro")
                .getPort(Service.class);
        return serviceRastro.buscaEventos("ECT", "SRO", "L", "T", "101", codigo);
    }

    private javax.xml.ws.Service getService(String urlWsdl, String nameSpaceUri, String localPart) throws Exception {
        try {
            URL url = new URL(urlWsdl);
            QName qname = new QName(nameSpaceUri, localPart);
            return javax.xml.ws.Service.create(url, qname);
        } catch (Exception e) {
            log.error("Erro ao obter o Service {}", e.getMessage(), e);
            throw new Exception(e);
        }
    }
}
