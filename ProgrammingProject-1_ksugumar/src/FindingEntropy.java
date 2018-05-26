import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;
import java.util.Vector;

public class FindingEntropy {
    
    int _cAtbs;
    int _cInst;
    public String _uLabs;
    public String _hLab;
    String _tColumn;
    ArrayList<String> _atbs = new ArrayList<String>();
    Vector<String> _vals = new Vector<String>();
    Vector<String> _cLabs = new Vector<String>();
    

    
    public double finalEValue(ArrayList<String> _dColumns, ArrayList<String> _uVals, ArrayList<String> _rLabels, ArrayList<String> _uLabs, int Flag) {
        double entropy = 0.0;
        int _ucVals = _uVals.size();
        // System.out.println(NumberOfDistinctValues);
        int _dcColumns = _dColumns.size();
        //System.out.println(NumberOfDecisionAttributes);
        int _cLabels = _rLabels.size();
        //System.out.println(NumberOfClassLabels);
        int _ucLabs = _uLabs.size();
        //System.out.println(NumberOfDistinctClassLabels);
        int sum = 0;
        double[] _entropyVar = new double[_ucVals];
        for (int i = 0; i < _ucVals; i++) {
            _entropyVar[i] = 0;
        }
        int[] _usColumn = new int[_ucVals];
        int[] ClassLabels = new int[_ucLabs];

        int[][] _signs = new int[_ucVals][_ucLabs];
        for (int i = 0; i < _ucVals; i++) {
            _usColumn[i] = 0;
            for (int j = 0; j < _ucLabs; j++) {
                _signs[i][j] = 0;
            }
        }

        for (int i = 0; i < _dcColumns; i++) {
            for (int j = 0; j < _ucVals; j++) {
                if (_dColumns.get(i).equals(_uVals.get(j))) {
                    for (int k = 0; k < _ucLabs; k++) {
                        if (_rLabels.get(i).equals(_uLabs.get(k))) {
                            _signs[j][k] = _signs[j][k] + 1;
                            //System.out.println(PosNeg[j][k]);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < _cInst; i++) {
            for (int j = 0; j < _ucLabs; j++) {
                if (_rLabels.get(i).equals(_uLabs.get(j))) {
                    ClassLabels[j]++;
                }
            }
        }
        if (Flag == 1) {
            for (int i = 0; i < _ucLabs; i++) {
                double b;
                // System.out.println("i"+i+"DistinctValues"+_cInst+""+_cLabs[i]);
                b = -((double) Math.log((double) ClassLabels[i] / (double) _cInst) / (double) Math.log(2));
                b = b * ((double) ClassLabels[i] / (double) _cInst);
                //System.out.println(b);
                entropy = entropy + b;
            }
        } else {

            for (int l = 0; l < _ucVals; l++) {
                sum = 0;
                for (int m = 0; m < _ucLabs; m++) {
                    sum = sum + _signs[l][m];

                }
                _usColumn[l] = sum;
                // System.out.println(DistinctAttributeValueSum[l]);
            }
            for (int l = 0; l < _ucVals; l++) {
                for (int m = 0; m < _ucLabs; m++) {
                    
                    if (_signs[l][m] != 0) {

                        double b = -(Math.log((double) _signs[l][m] / (double) _usColumn[l]) / (double) Math.log(2));
                        b = b * ((double) _signs[l][m] / (double) _usColumn[l]);
                        //System.out.println("b"+b);
                        _entropyVar[l] = b + _entropyVar[l];
                        //System.out.println("entropy"+entropyDV[l]);
                    }
                    // System.out.println(sum);
                }
               
                _entropyVar[l] = _entropyVar[l] * ((double) _usColumn[l] / (double) _dcColumns);

                entropy = entropy + _entropyVar[l];
                
            }

        }


        return entropy;
    }
    
    FindingEntropy(String _tColumn, Vector<String> _atbs, Vector<String> _vals, int _cAtbs, int _cInst, String _str) {
        this._tColumn = _tColumn;
        for (int i = 0; i < _atbs.size(); i++) {
            this._atbs.add(_atbs.get(i));
        }
        for (int i = 0; i < _vals.size(); i++) {
            this._vals.add(_vals.get(i));
        }
        this._cAtbs = _cAtbs;
        this._cInst = _cInst;
        this._uLabs = _str;
        this._hLab = "";
    }

    public double categorizeColumns(int _cId, int _check, int _fCheck) {
        ArrayList<String> _uVals = new ArrayList<String>();
        ArrayList<String> _dColumn = new ArrayList<String>();
        ArrayList<String> _cLabel1 = new ArrayList<String>();
        ArrayList<String> _uLabels = new ArrayList<String>();
        int _uValues = 0;
        int __ucLabs = 0;
//System.out.println("Attribute Number"+AttributeNumber);

        for (int i = 0; i < this._cInst; i++) {
            String line;
            int size;
            int flagDV = 0, flagCL = 0;
//System.out.println("Attribute Number"+AttributeNumber);
            line = _vals.get(i);
            String[] _sep = line.split(",");
            size = _sep.length;
            if (i == 0) {
            }
            int[] _valck = new int[this._cInst];
            int[] cLabck = new int[this._cInst];
            for (int j = 0; j < size; j++) {
                if (j == _cId && j != size - 1) {
                    _dColumn.add(i, _sep[j]);
                    if (i == 0) {
                        _uVals.add(i, _sep[j]);
                        _uValues++;
                    }
                    for (int k = 0; k < _uVals.size(); k++) {
                        if (_sep[j].equals(_uVals.get(k))) {
                            _valck[k] = 1;
                        }
                    }
                    for (int k = 0; k < _uVals.size(); k++) {
                        if (_valck[k] == 1) {
                            flagDV = 1;
                        }
                    }
                    if (flagDV == 0) {
                        _uValues++;
                        _uVals.add(_sep[j]);


                    }

                }
                if (j == size - 1) {
                    _cLabel1.add(i, _sep[j]);

                    if (i == 0) {
                        _uLabels.add(i, _sep[j]);
                        __ucLabs++;

                    }
                    for (int k = 0; k < _uLabels.size(); k++) {
                        if (_sep[j].equals(_uLabels.get(k))) {
                            cLabck[k] = 1;
                        }
                    }
                    for (int k = 0; k < _uLabels.size(); k++) {
                        if (cLabck[k] == 1) {
                            flagCL = 1;
                        }
                    }
                    if (flagCL == 0) {
                        __ucLabs++;
                        _uLabels.add(_sep[j]);


                    }

                }

            }

        }
        int[] CLCount = new int[_uLabels.size()];
        for (int i = 0; i < _uLabels.size(); i++) {
            for (int j = 0; j < _cLabel1.size(); j++) {
                if (_cLabel1.get(j).equals(_uLabels.get(i))) {
                    CLCount[i]++;
                }
            }
        }
        int max = FindingEntropy.findHighest(CLCount);
        this._uLabs = _uLabels.get(max);
        if (_fCheck == 1) {
//this._uLabs=DistinctClassLabels.get(0);
            if (__ucLabs == 1) {
                return 1.0;
            }
        } else {
            double entropy = finalEValue(_dColumn, _uVals, _cLabel1, _uLabels, _check);
//System.out.println(entropy);
            return entropy;
        }
        return 0.0;
    }

    public static int findHighest(int[] ti) {
        int maximum = ti[0];   // start with the first value
        for (int i = 1; i < ti.length; i++) {
            if (ti[i] > maximum) {
                maximum = ti[i];   // new maximum
            }
        }
        for (int i = 1; i < ti.length; i++) {
            if (ti[i] == maximum) {
                return i;   // new maximum
            }
        }
        return 0;
    }

    

    public double infoGain(double _cEntrop, double _colEntrop) {
        return (_cEntrop - _colEntrop);
    }
}
