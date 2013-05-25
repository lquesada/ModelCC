all: doc.pdf
	xpdf -z page doc.pdf

doc.dvi: *.tex *.eps *.bib Makefile
	latex doc
	echo bibtex doc
	latex doc
	latex doc

doc.pdf: doc.dvi Makefile
	dvipdf doc.dvi

clean:
	rm -rf *.aux *.dvi *.glo *.idx *.log *.toc *.pdf *.bbl *.blg

