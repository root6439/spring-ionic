package com.ares.springionic.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ares.springionic.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instanteDoPedido) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		
		pgto.setDataVencimento(cal.getTime());
		
	}
	
}
