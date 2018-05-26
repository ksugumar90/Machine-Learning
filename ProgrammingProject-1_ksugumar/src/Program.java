import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
import java.util.*;


public class Program {
    
    
    public String _lbl;
    public static int _post = 0;
    public static ArrayList<String> _nds = new ArrayList<String>();
    String _tColumn;
    Vector<String> _atbs = new Vector<String>();
    int _cVals;
    int _cIns;
    Vector<String> _vals = new Vector<String>();
    Vector<String> _tVals = new Vector<String>();
    int _cTVals;

    public static void main(String[] args) throws Exception {
        String _verdict = "";
        int _ctr = 0;
        System.out.println("-------------------------------------------");
        System.out.println("Implementation of ID3 algorithm - ksugumar - v3.8");
        System.out.println("-------------------------------------------");
        System.out.println(" Please proceed with the choice of dataset you would like to implement the ID3 algorithm on");
        System.out.println("1.Dataset of Mushroom\n2.Dataset of Car\n3.Dataset of Breast-Cancer\n4.Dataset of Letter-Recognition\n5.Dataset of Ecoli");
        String _fName = "";
        Scanner _sinput5 = new Scanner(System.in);
        int option = _sinput5.nextInt();
        String _dirName=System.getProperty("user.dir");
        switch (option) {
            case 1:
                _fName = _dirName+"\\dataset\\mushroom.csv";
                break;
            case 2:
                _fName = _dirName+"\\dataset\\car.data";
                break;
            case 3:
                _fName = _dirName+"\\dataset\\bc.csv";
                break;
            case 4:
                _fName = _dirName+"\\dataset\\letter-recognition.csv";
                break;
            case 5:
                _fName = _dirName+"\\dataset\\ecolifinal.csv";
                break;
            default:
                System.out.println("Please enter N or n");
                System.exit(0);
                break;
        }
        do {
            int[] _sValue = new int[11];
            int[] _iSize = new int[11];
            double[] _avgAccr = new double[10];
            for (int k = 0; k < 1; k++) {
                if (_ctr != 0) {
                    System.out.println("Dataset randomization");
                    Program.dataShift(_fName);
                }
                for (int i = 0; i < 5; i++) {
                    _sValue[i] = 0;
                }
                System.out.println("After doing 5-fold validation");
                for (int y = 0; y < 5; y++) {
                    //System.out.println("Doing Fold: " + y );
                    Program t = new Program(y, _fName);
                    //  System.out.println(t._tColumn);
                    //System.out.println(t._cIns);
                    //System.out.println(t._cVals);
                    Vector<String> _atb2 = t._atbs;

                    _iSize[y] = t._tVals.size();
                    String endcheck = "";

                    StructTree _nodeObj = Program.algImplement(t);

                    
                    for (int z = 0; z < t._tVals.size(); z++) {
                        String line = t._tVals.get(z);
                        String[] split = line.split(",");
                        int size = split.length;
                        String[] Values = new String[size];
                        for (int j = 0; j < size; j++) {
                            if (j == size - 1) {
                                endcheck = split[j];
                            }
                            Values[j] = split[j];
                        }
                        String finalValues = _nodeObj.classifyDatapoint(_atb2, Values);
                        // System.out.println(result);
                        if (finalValues.equals(endcheck)) {
                            //   System.out.println("Correct Classification");
                            _sValue[y]++;
                        }
                    }
                    //System.out.println("Correct"+y);
                }
                double correctSum = 0;
                System.out.println("RESULTS");
                System.out.println("---------------------------------------");
                for (int x = 0; x < 5; x++) {
                    System.out.println("Result of Fold:" + x  );
                    System.out.println("Instances tested:" + _iSize[x]);
                    System.out.println("Matched:" + _sValue[x]);
                    System.out.println("---------------------------------------");
                }
                for (int x = 0; x < 5; x++) {
                    if (_iSize[x] != 0) {
                        correctSum = correctSum + ((double) _sValue[x] / _iSize[x]);
                    }
                }
                _avgAccr[k] = ((correctSum / 5) * 100);
            }
            double avaSum = 0;
            for (int k = 0; k < 1; k++) {
                avaSum = avaSum + _avgAccr[k];
            }
            System.out.println("Average Accuracy:" + ((avaSum)));
            Scanner in1 = new Scanner(System.in);
            System.out.println("Please press N to close");
            _verdict = in1.next();
            _ctr++;
        } while (_verdict.equals("y"));
    }
    
    
    public Treesubs[] findSets(String _atb, Program t) {
        int _atbId = 0, _size = 0, _flagId = 0;
        String _line, _join = "";
        ArrayList<String> _dVals = new ArrayList<String>();
        ArrayList<String> _dColumn = new ArrayList<String>();
        ArrayList<String> _cLabel = new ArrayList<String>();
        ArrayList<String> _dLabel = new ArrayList<String>();
        int _uVals = 0;
        int[] _cLabFlag = new int[this._cIns];
        int _ucLab = 0;
        String[] _sep = new String[this._cIns];
        String[] _var2 = new String[this._cIns];
        for (int i = 0; i < this._atbs.size(); i++) {
            if (_atb.equals(this._atbs.get(i))) {
                _atbId = i;
                //  System.out.println(i + "," + _atbs.get(i));
            }
        }
        for (int i = 0; i < this._cIns; i++) {

            int flag = 0;
            _line = _vals.get(i);
            String[] _sep2 = _line.split(",");
            _size = _sep2.length;
            int[] _iFlag = new int[this._cIns];
            for (int j = 0; j < _size; j++) {

                if (j == _atbId) {
                    _dColumn.add(i, _sep2[j]);
                    if (i == 0) {
                        _dVals.add(i, _sep2[j]);
                        _uVals++;
                    }
                    for (int k = 0; k < _dVals.size(); k++) {
                        if (_sep2[j].equals(_dVals.get(k))) {
                            _iFlag[k] = 1;
                        }
                    }
                    for (int k = 0; k < _dVals.size(); k++) {
                        if (_iFlag[k] == 1) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        _uVals++;
                        _dVals.add(_sep2[j]);


                    }
                }
            }
        }


        int[] _ucVal = new int[_uVals];
        for (int i = 0; i < this._cIns; i++) {
            String _nVal;
            _nVal = this._vals.get(i);
            String[] _sep3 = _nVal.split(",");

            _size = _sep3.length;
            int flag = 0;
            //System.out.println(this._vals.get(i));

            for (int k = 0; k < _size; k++) {
                if (k == _atbId) {
                    for (int j = 0; j < _uVals; j++) {
                        if (_sep3[k].equals(_dVals.get(j))) // System.out.println(this._vals.get(i));
                        {
                            _sep[i] = _sep3[k];
                            _sep3[k] = "";
                            _ucVal[j]++;
                        }

                    }
                }

            }
            _join = "";
            for (int k = 0; k < _size; k++) {
                if (k == 0) {
                    _join = _join + _sep3[k];
                } else if (k == 1 && _atbId == 0) {
                    _join = _join + _sep3[k];
                } else if (k == _atbId) {
                    _join = _join + _sep3[k];
                } else {
                    _join = _join + "," + _sep3[k];
                }

            }

            _var2[i] = _join;


        }
        ArrayList<String[]> a = new ArrayList<String[]>();
        String[] _var4;
        for (int j = 0; j < _uVals; j++) {
            int k = _ucVal[j];
            _var4 = new String[k];
            int z = 0;
            for (int i = 0; i < this._cIns; i++) {
                if (z <= k) {
                    if (_sep[i].equals(_dVals.get(j))) {
                        _var4[z] = _var2[i];
                        z++;

                    }
                }
            }

            a.add(j, _var4);
        }
        Vector<String> _vec1 = new Vector<String>();
        _vec1 = this._atbs;
//c.remove(AttributeNumber);
        Treesubs[] _var5 = new Treesubs[a.size()];
        for (int j = 0; j < a.size(); j++) {
            _var5[j] = new Treesubs();
        }
        for (int j = 0; j < a.size(); j++) {
            String[] _var6 = a.get(j);
            int z = 0;
            _var5[j]._labs = _dVals.get(j);
            for (int i = 0; i < _var6.length; i++) {

                _var5[j].tVals.add(i, _var6[i]);
            }
            _var5[j]._cVals = this._cVals - 1;
            for (int k = 0; k < t._atbs.size(); k++) {
                if (!t._atbs.get(k).equals(_atb)) {
                    _var5[j]._atbs.add(z, t._atbs.get(k));
                    z++;
                }


                //System.out.println("Attribute Number"+Attribute);
                //   System.out.println("Removing Attribute"+Attribute);
                // sb[j]._atbs.remove(Attribute);
                _var5[j]._cInst = _ucVal[j];
                // System.out.println(j + sb[j].tVals.get(i));
            }
        }
        return _var5;

    }

    Program(int _bValue, String _fName) throws Exception {
        File _fiName = new File(_fName);
        File _fiName2 = new File(_fName);
        Scanner _scanInput = new Scanner(_fiName2);
        Scanner _scanInput2 = new Scanner(_fiName);
        int _tCount = 0, _size, _countVal2 = 0;
        String line;
        double Lcount = 0;
        this._cTVals = 0;
        while (_scanInput.hasNextLine()) {
            _scanInput.nextLine();
            Lcount++;
            // System.out.println(Lcount);

        }
        Lcount = Math.round((Lcount) / 5);
        //   System.out.println("Lcount"+Lcount);
        double _var = 0;
        while (_scanInput2.hasNextLine()) {
            line = _scanInput2.nextLine();
            // if(!(z>Lcount && (Lcount*2)<z))
            // {
            if (_tCount == 0) {

                String[] split = line.split(",");
                _size = split.length;
                this._cVals = _size - 1;
                for (int j = 0; j < _size; j++) {
                    if (j == (_size - 1)) {
                        this._tColumn = split[j];
                        //                 System.out.println(split[j]);
                    } else {
                        this._atbs.add(_countVal2, split[j]);
                        //               System.out.println(aCount + "," + this._atbs.get(aCount));
                    }
                    _countVal2++;
                }
                _tCount++;
            } else {
    
                if (!(_var > (Lcount * _bValue) && _var < (Lcount * (_bValue + 1)))) {
                    this._vals.add(_cIns, line);
                    _tCount++;
                    this._cIns++;
                } else {

                    this._tVals.add(_cTVals, line);
// System.out.println(this._tVals.get(this._cTVals));
                    this._cTVals++;

                }
            }
            this._lbl = "";
            //     System.out.println("Lines " + count);
            //   }
            _var++;
        }

    }

    Program() {
    }

    public static void dataShift(String file_fname) throws Exception {
        File file = new File(file_fname);
        Vector<String> _vec2 = new Vector<String>();
        Scanner _sInput = new Scanner(file);
        int _var8 = 0, _var9 = 0;
        String line = "";
        Random _rad = new Random();
        while (_sInput.hasNextLine()) {
            if (_var8 == 0) {
                line = _sInput.nextLine();
                System.out.println(line);
            } else {
                _vec2.add(_var9, _sInput.nextLine());
//System.out.println(values.get(m));
                _var9++;
            }
            _var8++;
        }
        for (int i = 0; i < _vec2.size(); i++) {
            int middle = _vec2.size() / 2;
            int first = _rad.nextInt(middle);
            int last = _rad.nextInt(_vec2.size() - 1);
            Collections.swap(_vec2, first, last);

        }
        _sInput.close();
        FileWriter fstream = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fstream);
        out.append(line);
        out.newLine();
        for (int i = 0; i < _vec2.size(); i++) {
            //System.out.println(values.get(i));
            out.append(_vec2.get(i));
            out.newLine();

        }
        out.close();
        fstream.close();
    }
    
    
    Program(int _cVals, int _cVali, String _tcols, Vector<String> _atbs, Vector<String> _vals) {
        this._cVals = _cVals;
        this._cIns = _cVali;
        this._tColumn = _tcols;
        this._atbs = _atbs;
        this._vals = _vals;
        this._lbl = "";
    }

    

    public static int _highest(double[] ti) {
        double max = ti[0];   // start with the first value
        for (int i = 1; i < ti.length; i++) {
            if (ti[i] > max) {
                max = ti[i];   // new maximum
            }
        }
        for (int i = 0; i < ti.length; i++) {
            if (ti[i] == max) {
                return i;   // new maximum
            }
        }
        return 0;
    }

    public static StructTree algImplement(Program p) throws Exception {
//try
//{
        double check;
        FindingEntropy _entro = new FindingEntropy(p._tColumn, p._atbs, p._vals, p._cVals, p._cIns, "");
        check = _entro.categorizeColumns(0, 0, 1);
//System.out.println("check"+check);
        if (check == 1) {

            Program._nds.add(_entro._uLabs);
//comma with answer, release flag
//System.out.println(e._uLabs);
            return new StructTree(_entro._uLabs, "L Node", p._lbl);

        } else if (p._cVals == 0) {

            Program._nds.add("Done");
            return new StructTree(_entro._uLabs, "L Node", p._lbl);

        } else {
            double[] _infoG = new double[p._cVals];
            for (int i = 0; i < p._cVals; i++) {
                double _entValue1 = _entro.categorizeColumns(p._cVals + 1, 1, 0);
                double _entValue2 = _entro.categorizeColumns(i, 0, 0);
                _infoG[i] = _entro.infoGain(_entValue1, _entValue2);

                // System.out.println("Information infoGain..."+e.infoGain(cEntropy, aEntropy));
            }
            int id = _highest(_infoG);
//System.out.println("id: "+id);
            String _dColumn = p._atbs.get(id);

//check flag
//System.out.println("_varb"+DecisionAttribute);
//add rule
            StructTree _dNode = new StructTree(_dColumn, "empty");
            Program._nds.add(_dColumn);
            ArrayList<String[]> _arr = new ArrayList<String[]>();
            Treesubs[] _var6;
            _var6 = p.findSets(_dColumn, p);

            Program[] _var7 = new Program[_var6.length];
            for (int j = 0; j < _var6.length; j++) {

                _var7[j] = new Program();
            }
            for (int j = 0; j < _var6.length; j++) {
                _var7[j]._cVals = _var6[j]._cVals;
                //    System.out.println(j+","+ti[j]._cVals);
                _var7[j]._atbs = _var6[j]._atbs;
                //  System.out.println(j+","+ti[j]._atbs);
                //System.out.println(j+"Attrib size,"+ti[j]._atbs.size());
                _var7[j]._vals = _var6[j].tVals;
                //System.out.println(j+","+ ti[j]._vals);
                _var7[j]._cIns = _var6[j]._cInst;
                //System.out.println(j+","+ ti[j]._cIns);
                _var7[j]._tColumn = p._tColumn;
                //System.out.println(j+","+ ti[j]._tColumn);
                _var7[j]._lbl = _var6[j]._labs;
            }
            for (int j = 0; j < _var6.length; j++) {
                if (_var7[j]._cIns == 0) {
                    return new StructTree(_entro._uLabs, "Leaf", p._lbl);
                } else {//System.out.println("Second Recursion");
                    if (j == 0) {
                        Program._post++;
                    }
                    //and the _lbl
                    StructTree subtree = algImplement(_var7[j]);
                    subtree._lab = _var7[j]._lbl;
                    _dNode.addChildNode(_var7[j]._lbl, subtree);
                }
            }
            return _dNode;
        }/*}
        catch(Exception e)
        {
        System.out.println(t._cVals);
        System.out.println(t._atbs);
        System.out.println(t._vals);
        System.out.println(e.getStackTrace());
        } */
    }

    

    
}
