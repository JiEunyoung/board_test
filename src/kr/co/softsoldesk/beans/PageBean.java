package kr.co.softsoldesk.beans;

public class PageBean {

	private int min; //�ּ� ������ ��ȣ
	private int max; //�ִ� ������ ��ȣ
	private int prevPage; //���� ��ư ������ ��ȣ
	private int nextPage; //���� ��ư ������ ��ȣ
	private int pageCnt; //��ü ������ ����
	private int currentPage; //���� ������ ��ȣ
	
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		//contentCnt: ��ü �� ����
		//currentPage: ���� ������ ��ȣ
		//contentPageCnt: �������� ���� ����(10��)
		//paginationCnt: �ѹ��� ǥ���� ������ ��ư�� �ִ밳��(10��)
		
		this.currentPage = currentPage;
		//���� ������ ��ȣ
		
		pageCnt = contentCnt / contentPageCnt;
		//��ü ������ ����
		//EX) �Խñ��� 1��: 1 / 10 => 0������ => +1 �ʿ�
		//EX) �Խñ��� 10��: 10 / 10 => 1������ => +1�ʿ� X
		//EX) �Խñ� 21�� : 21/10 => 2������ => +1 �ʿ�
		//EX) �Խñ� 30��: 3������ => +1 �ʿ�X
		//=> �������� 0���� ũ�� +1 �ʿ�
		
		if(contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}
		//=================������ ���====================
		
		min = ((currentPage-1) / contentPageCnt) * contentPageCnt + 1;
		//1��° ������: 0 / 10 * 10+1 => 1 [1]���� ȭ�鿡 ���
		//2��° ������: 1/10*10+1 => 1     [1]���� ȭ�鿡 ���
		//11��° ������ 10/10*10+1 => 11   [11]���� ȭ�鿡 ���
		
		max = min + paginationCnt - 1;
		//1������: 1 + 10 - 1 => 10 1�������� �ִ� ������[10]
		
		if(max > pageCnt) {
			max = pageCnt; //11���� ���� ���� �� �ϴܿ� ���������̼��� 2�������� ����
		}
		
		//=================���̴� �������� ���������̼� �� �ִ�, �ּ�==================
		
		prevPage = min - 1;
		//���� �������� �ּ�����������-1   <���� [11]  => [10]
		
		nextPage = max + 1;
		//���� �������� �ִ�����������+1   [10] ����>  => [11]
		
		if(nextPage > pageCnt) {
			nextPage = pageCnt;
		} //�ִ������� ����
		//EX)���� ��ü �������� 15�������� ��, max�� 15�������� ����
		//nextPage�� 16���������� ������ 16�������� �������� �����Ƿ� nextPage�� 15�������� ����
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
