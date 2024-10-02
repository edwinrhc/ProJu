package com.stfonavi.proju.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRenderbackup<T> {

    private final String url;
    private final Page<T> page;

    private final int totalPaginas;

    private final int numElementosPorPagina;

    private final int paginaActual;

    private final List<PageItem> paginas;

    private final String nombre; // Agregar este atributo

	public PageRenderbackup(String url, Page<T> page, String nombre) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();


		numElementosPorPagina = 6;
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;

        this.nombre = nombre; // Asignar el valor del parámetro nombre al atributo

		int desde, hasta;
		if (totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (paginaActual <= numElementosPorPagina / 2) {
				desde = 1;
				hasta = numElementosPorPagina;
			} else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			} else {
				desde = paginaActual - numElementosPorPagina / 2;
				hasta = numElementosPorPagina;
			}
		}

		for (int i = 0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}

	}



    public String getUrl() {
        return url;
    }

    public Page<T> getPage() {
        return page;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getNumElementosPorPagina() {
        return numElementosPorPagina;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public boolean isFirst(){
        return page.isFirst();
    }
    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }
    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

    // Agregar este método para obtener la URL de paginación con el parámetro de búsqueda
    public String getUrlPage(int numPage) {
        return url + "?nombre=" + nombre + "&page=" + numPage;
    }
}
