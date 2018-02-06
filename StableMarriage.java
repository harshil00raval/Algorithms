public class StableMarriage {

    public static void stableMarriages(int M[][],int W[][]){
        int pInd[] = new int[M.length];
        int aInd[] = new int[W.length];
        int ppair[] = new int[M.length];
        int apair[] = new int[W.length];

        for(int i = 0 ; i < M.length;i++){
            ppair[i] = -1;
            apair[i] = -1;
        }

        boolean repeat = true;
        while(repeat){
            for(int i = 0;i<M.length;i++){
                for(int j =0;j<M[i].length;j++){
                    if(ppair[i] != -1)
                        break;
                    int choiceInd = pInd[i];
                    int choice = M[i][choiceInd];

                    //check if acceptor is available
                    if(apair[choice] == -1){
                        apair[choice] = i;
                        ppair[i] = choice;
                        pInd[i] = pInd[i]+1;
                        break;
                    }
                    else if(betterProposer(W[choice],i,apair[choice])){
                        ppair[apair[choice]] = -1;
                        ppair[i] = choice;
                        apair[choice] = -1;
                        apair[choice] = i;
                        pInd[i] = pInd[i]+1;
                        break;
                    }
                    else{
                        pInd[i] = pInd[i]+1;
                    }
                }
            }

            for(int i = 0 ; i < M.length;i++){
                repeat = false;
                if(ppair[i] == -1) {
                    repeat = true;
                    break;
                }
            }
        }
        for(int i = 0 ; i < M.length;i++){
            System.out.println(ppair[i]);
        }
    }

    public static void main(String args[]){
        int a[][] = {{2,3,4,1,0},{0,2,1,3,4},{1,2,4,0,3},{2,3,4,0,1},{1,2,3,0,4}};
        int b[][] = {{3,2,0,1,4},{4,3,1,2,0},{3,0,2,1,4},{4,1,2,0,3},{3,1,0,2,4}};
        stableMarriages(b,a);
        System.out.println(a[2]);

    }
    public static boolean betterProposer(int accaptor[], int proposer, int currProposer){
        for(int i=0;i<accaptor.length;i++){
            if(accaptor[i]== proposer)
                return true;
            if(accaptor[i]== currProposer)
                return false;
        }
        return false;
    }
}
