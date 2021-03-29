package com.stormnet.net.server.dao.xml;

import com.stormnet.net.data.description.Description;
import com.stormnet.net.server.dao.DescriptionDao;
import com.stormnet.net.server.dao.xml.db.XmlDataBase;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class XmlDescriptionDao implements DescriptionDao {

    @Override
    public Description readDescription(Long id) {
        try {
            Element detailsTag = getDetailsTagById(id);
            if (detailsTag == null) {
                return null;
            }

            Description description = getDetailsDataFromTag(detailsTag);
            return description;

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private Element getDetailsTagById(Long descrId) {

        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("description");

        NodeList allDetailsTags = document.getElementsByTagName("details");
        int tagCount = allDetailsTags.getLength();

        for (int i = 0; i < tagCount; i++) {
            Element detailsTag = (Element) allDetailsTags.item(i);

            Element idTag = (Element) detailsTag.getElementsByTagName("id").item(0);
            String idStr = idTag.getTextContent();
            Long dbId = Long.parseLong(idStr);

            if (dbId.equals(descrId)) {
                return detailsTag;
            }
        }
        return null;
    }

    private Description getDetailsDataFromTag(Element detailsTag) {
        Element idTag = (Element) detailsTag.getElementsByTagName("id").item(0);
        Long id = NumbersUtils.parseLong(idTag.getTextContent());

        Element fullDescriptionTag = (Element) detailsTag.getElementsByTagName("fullDescription").item(0);
        String fullDescription = fullDescriptionTag.getTextContent();

        Description description = new Description(fullDescription);
        description.setId(id);

        return description;
    }
}
